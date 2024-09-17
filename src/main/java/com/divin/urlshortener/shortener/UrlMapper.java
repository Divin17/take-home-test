package com.divin.urlshortener.shortener;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shortener")
public class UrlMapper {

    @Id
    private String id;
    private String originalUrl;
    private LocalDateTime expiration;

    public UrlMapper(){}

    public UrlMapper(String originalUrl, String id, LocalDateTime expiration) {
        this.originalUrl = originalUrl;
        this.id = id;
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
