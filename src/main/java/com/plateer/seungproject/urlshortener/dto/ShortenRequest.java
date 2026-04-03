package com.plateer.seungproject.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortenRequest {

    @NotBlank(message = "URL을 입력해주세요.")
    @Pattern(
        regexp = "^https?://.*",
        message = "http:// 또는 https:// 로 시작하는 URL을 입력해주세요."
    )
    private String originalUrl;
}