package com.divin.urlshortener.shortener;

import com.divin.urlshortener.shortener.UrlValidation.ValidUrl;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UrlMapperRequest(@Nullable String id,
                               @NotBlank(message = "Original URL must not be blank")
                               @ValidUrl String originalUrl,
                               @Nullable @Positive(message = "Time To Leave must be a positive integer") Integer ttl) {
}
