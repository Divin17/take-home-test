package com.divin.urlshortener.shortener.UrlValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isBlank()) {
            return true;
        }

        try {
            URI parsedUri = new URI(url);
            return "https".equalsIgnoreCase(parsedUri.getScheme()) && parsedUri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
