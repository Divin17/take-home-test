package com.divin.urlshortener.shortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlMapperController.class)
@ComponentScan(basePackageClasses = UrlMapperRequest.class)
class UrlMapperControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UrlMapperService service;

    private final List<UrlMapper> urls = new ArrayList<>();

    @BeforeEach
    void setup(){
        urls.add(new UrlMapper("https://google.com",
                "4dhf32s",
                LocalDateTime.now().plusMinutes(300)));
    }

    @Test
    void shouldCreateNewUrlMapper() throws Exception{

        var request = new UrlMapperRequest("5dsfhe3", "https://www.google.com",500);
        mvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    void shouldRedirectToOriginalUrl() throws Exception{

        mvc.perform(get("/4dhf32s"))
                .andExpect(status().isFound());
    }

    @Test
    void shouldDeleteUrlMapper() throws Exception{
        mvc.perform(delete("/shorten/4dhf32s")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urls.getFirst())))
                .andExpect(status().isNoContent());

    }
}