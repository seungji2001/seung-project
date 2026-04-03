package com.plateer.seungproject.urlshortener.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortenResponse {
    private String shortCode;
    private String shortUrl;
    private String originalUrl;
}