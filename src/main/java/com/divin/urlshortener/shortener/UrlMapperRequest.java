package com.divin.urlshortener.shortener;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UrlMapperRequest(@Nullable String id,
                               @NotBlank(message = "Original URl must not be blank") String originalUrl,
                               @Nullable @Positive(message = "Time To Leave must be a positive integer") Integer ttl) {
}
