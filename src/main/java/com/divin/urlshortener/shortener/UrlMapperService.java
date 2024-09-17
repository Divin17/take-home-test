package com.divin.urlshortener.shortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlMapperService {

    private static final Logger log = LoggerFactory.getLogger(UrlMapperService.class);
    private final UrlMapperRepository repository;
    private final Environment environment;

    public UrlMapperService(UrlMapperRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    public String shortenUrl(String originalUrl, String id, Integer ttl){
        var baseUrl = getBaseUrl();
        var urlId = (id != null) ? id : generateShortUrl();
        log.info("Checking if urlId exist {}", urlId);
        if(repository.existsById(urlId)){
            log.error("URL ID ALREADY CREATED AND IN USE --> {}", id);
            throw new IllegalArgumentException("URL ID already in use!");
        }
        LocalDateTime expiration = (ttl != null) ? LocalDateTime.now().plusSeconds(ttl) : null;
        UrlMapper urlMapper = new UrlMapper(originalUrl, urlId, expiration);
        repository.save(urlMapper);
        return baseUrl + "/" + urlId;
    }

    public String getOriginalUrl(String id){
        Optional<UrlMapper> urlMapperOptional = repository.findById(id);

        if(urlMapperOptional.isEmpty()){
            throw new UrlNotFoundException();
        }
        UrlMapper urlMapper = urlMapperOptional.get();

        //delete expired by TTL
        if (urlMapper.getExpiration() != null &&
                    LocalDateTime.now().isAfter(urlMapper.getExpiration())) {
                repository.delete(urlMapper);
                throw new UrlExpiredException();
        }
        return urlMapper.getOriginalUrl();
    }

    public void deleteUrl(String id) {
        Optional<UrlMapper> urlMapperOptional = repository.findById(id);
        if (urlMapperOptional.isEmpty()) {
            log.error("URL NOT FOUND {}", id);
            throw new UrlNotFoundException();
        }
        repository.delete(urlMapperOptional.get());

    }

    private String generateShortUrl(){
        var uuid = UUID.randomUUID().toString().replace("-","").substring(0,3);
        log.info("Generated short url -> {}", uuid);
        return uuid;
    }

    private String getBaseUrl() {

        return environment.getProperty("app.base-url");
    }
}
