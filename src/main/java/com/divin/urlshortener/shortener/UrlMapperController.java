package com.divin.urlshortener.shortener;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlMapperController {

    private final UrlMapperService urlMapperService;

    public UrlMapperController(UrlMapperService urlMapperService) {
        this.urlMapperService = urlMapperService;
    }

    @PostMapping("/shorten")
    ResponseEntity<String> shortenUrl(@Valid @RequestBody UrlMapperRequest request){
        String shortUrl = urlMapperService.shortenUrl(request.originalUrl(), request.id(), request.ttl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{id}")
    ResponseEntity<Void> redirectUrl(@PathVariable("id") String id){
        String originalUrl = urlMapperService.getOriginalUrl(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("shorten/{id}")
    public void deleteShortenedUrl(@PathVariable String id) {
        urlMapperService.deleteUrl(id);
    }
}
