package com.divin.urlshortener.shortener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlMapperControllerIntTest {

    private static final Logger log = LoggerFactory.getLogger(UrlMapperControllerIntTest.class);
    @LocalServerPort
    int server_port;

    RestClient restClient;

    @BeforeEach
    void setup(){
        restClient = RestClient.create("http://localhost:"+server_port);
    }

    @Test
    void shouldShortenUrl(){

        UrlMapper urlMapper = new UrlMapper(
                "https://google.com",
                "4jfhre",
                LocalDateTime.now().plusSeconds(500)
        );
        ResponseEntity<String> response = restClient.post()
                .uri("/shorten")
                .body(urlMapper)
                .retrieve()
                .toEntity(String.class);

        String body = response.getBody();

        log.info("The responses is {}", response.getStatusCode());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert body != null;
        assertTrue(body.startsWith("http"));
        assertTrue(body.endsWith("4jfhre"));
    }

    @Test
    void shouldGetOriginalUrl(){

        UrlMapper urlMapper = new UrlMapper(
                "https://youtube.com",
                "5dfasd8",
                LocalDateTime.now().plusSeconds(500)
        );

        ResponseEntity<Void> newUrlMapper = restClient.post()
                .uri("/shorten")
                .body(urlMapper)
                .retrieve()
                .toBodilessEntity();

        ResponseEntity<Void> response = restClient.get()
                .uri("/5dfasd8")
                .retrieve()
                .toBodilessEntity();

        String url = String.valueOf(response.getHeaders().getLocation());
        log.info("This the returned URL ==> {}", url);

        assertNotNull(response.getHeaders().getLocation());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("https://youtube.com", url);

    }

    @Test
    void shouldDeleteUrlMapper(){

        UrlMapper urlMapper = new UrlMapper(
                "https://youtube.com",
                "5dfasd8",
                LocalDateTime.now().plusSeconds(500)
        );

        ResponseEntity<Void> newUrlMapper = restClient.post()
                .uri("/shorten")
                .body(urlMapper)
                .retrieve()
                .toBodilessEntity();

        ResponseEntity<Void> run = restClient.delete()
                .uri("shorten/5dfasd8")
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, run.getStatusCode());
    }

}