package com.plateer.seungproject.urlshortener.controller;

import com.plateer.seungproject.urlshortener.dto.ShortenRequest;
import com.plateer.seungproject.urlshortener.dto.ShortenResponse;
import com.plateer.seungproject.urlshortener.dto.StatsResponse;
import com.plateer.seungproject.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    /**
     * URL 단축 요청
     * POST /api/shorten
     * Body: { "originalUrl": "https://..." }
     */
    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest request) {
        ShortenResponse response = urlService.shorten(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 단축 URL 리다이렉트
     * GET /{shortCode}  →  302 redirect
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(302)
                .location(URI.create(originalUrl))
                .build();
    }

    /**
     * 통계 조회
     * GET /api/stats/{shortCode}
     */
    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<StatsResponse> stats(@PathVariable String shortCode) {
        StatsResponse response = urlService.getStats(shortCode);
        return ResponseEntity.ok(response);
    }
}