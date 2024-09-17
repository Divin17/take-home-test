package com.divin.urlshortener.shortener;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class UrlExpiredException extends RuntimeException{
    public UrlExpiredException() {
        super("Url Expired!!");
    }
}
