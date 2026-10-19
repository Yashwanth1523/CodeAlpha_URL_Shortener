package com.codealpha.urlshortener.dto;

import java.time.LocalDateTime;

public record ShortUrlResponse(
    String originalUrl,
    String shortCode,
    String shortUrl,
    LocalDateTime createdAt
) {}
