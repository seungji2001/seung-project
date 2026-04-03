package com.plateer.seungproject.urlshortener.service;

import com.plateer.seungproject.urlshortener.dto.ShortenRequest;
import com.plateer.seungproject.urlshortener.dto.ShortenResponse;
import com.plateer.seungproject.urlshortener.dto.StatsResponse;
import com.plateer.seungproject.urlshortener.entity.UrlMapping;
import com.plateer.seungproject.urlshortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final String REDIS_KEY_PREFIX = "url:";
    private static final Duration REDIS_TTL = Duration.ofHours(24);

    private final UrlMappingRepository urlMappingRepository;
    private final StringRedisTemplate redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public ShortenResponse shorten(ShortenRequest request) {
        String shortCode = generateUniqueCode();

        UrlMapping urlMapping = UrlMapping.builder()
                .shortCode(shortCode)
                .originalUrl(request.getOriginalUrl())
                .build();
        urlMappingRepository.save(urlMapping);

        // Redis에 캐싱
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + shortCode, request.getOriginalUrl(), REDIS_TTL);

        log.debug("URL 단축 완료: {} -> {}", request.getOriginalUrl(), shortCode);

        return ShortenResponse.builder()
                .shortCode(shortCode)
                .shortUrl(baseUrl + "/" + shortCode)
                .originalUrl(request.getOriginalUrl())
                .build();
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        // 1. Redis 캐시 조회
        String cachedUrl = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + shortCode);
        if (cachedUrl != null) {
            log.debug("Redis 캐시 히트: {}", shortCode);
            urlMappingRepository.incrementClickCount(shortCode);
            return cachedUrl;
        }

        // 2. DB 조회 (캐시 미스)
        log.debug("Redis 캐시 미스, DB 조회: {}", shortCode);
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 단축 코드입니다: " + shortCode));

        // Redis에 다시 캐싱
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + shortCode, urlMapping.getOriginalUrl(), REDIS_TTL);
        urlMappingRepository.incrementClickCount(shortCode);

        return urlMapping.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public StatsResponse getStats(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 단축 코드입니다: " + shortCode));

        return StatsResponse.builder()
                .shortCode(urlMapping.getShortCode())
                .originalUrl(urlMapping.getOriginalUrl())
                .clickCount(urlMapping.getClickCount())
                .createdAt(urlMapping.getCreatedAt())
                .build();
    }

    private String generateUniqueCode() {
        String code;
        int attempts = 0;
        do {
            code = generateRandomCode();
            attempts++;
            if (attempts > 10) {
                throw new RuntimeException("단축 코드 생성에 실패했습니다. 잠시 후 다시 시도해주세요.");
            }
        } while (urlMappingRepository.existsByShortCode(code));
        return code;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS.charAt(secureRandom.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}