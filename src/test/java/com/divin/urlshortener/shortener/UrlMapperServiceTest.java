package com.divin.urlshortener.shortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlMapperServiceTest {

    @Autowired
    private UrlMapperService service;

    @MockBean
    private UrlMapperRepository repository;

    @Test
    void shouldShortenUrl() {
        // Mock data
        String originalUrl = "https://google.com";
        String id = "rut3e2e";
        Integer ttl = 3600;

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(any())).thenReturn(new UrlMapper(originalUrl, id, LocalDateTime.now()));

        String shortUrl = service.shortenUrl(originalUrl, id, ttl);

        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("http"));
        assertTrue(shortUrl.endsWith(id));

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldGetOriginalUrl() {
        // Mock data
        String id = "4389fds";
        UrlMapper urlMapper = new UrlMapper("https://google.com", id, LocalDateTime.now().plusHours(1));

        when(repository.findById(id)).thenReturn(Optional.of(urlMapper));

        String originalUrl = service.getOriginalUrl(id);

        assertNotNull(originalUrl);
        assertEquals("https://google.com", originalUrl);

        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteUrl() {
        // Mock data
        String id = "frji345";
        UrlMapper urlMapper = new UrlMapper("https://google.com", id, LocalDateTime.now().plusHours(1));

        when(repository.findById(id)).thenReturn(Optional.of(urlMapper));
        doNothing().when(repository).delete(urlMapper);

        service.deleteUrl(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).delete(urlMapper);
    }
}