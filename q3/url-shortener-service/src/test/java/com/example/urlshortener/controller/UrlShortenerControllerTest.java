package com.example.urlshortener.controller;

import com.example.urlshortener.config.property.ServiceConfigProperties;
import com.example.urlshortener.controller.dto.NewUrlRequest;
import com.example.urlshortener.controller.dto.NewUrlResponse;
import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import com.example.urlshortener.service.UrlShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.urlshortener.prototype.controller.dto.NewUrlResponsePrototypes.aNewUrlResponse;
import static com.example.urlshortener.prototype.repository.entity.ShortenedUrlEntityPrototypes.aShortenedUrlEntity;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlShortenerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private UrlShortenerService urlShortenerService;
    private ServiceConfigProperties serviceConfigProperties;
    private String fqdnProperty = "http://localhost:8080";

    @BeforeEach
    void setup() {
        urlShortenerService = mock(UrlShortenerService.class);
        serviceConfigProperties = new ServiceConfigProperties(fqdnProperty);

        mockMvc = MockMvcBuilders.standaloneSetup(new UrlShortenerController(urlShortenerService, serviceConfigProperties))
                .setConversionService(new DefaultFormattingConversionService())
                .build();
    }

    @Test
    void newUrlTest() throws Exception {
        NewUrlRequest mockRequest = new NewUrlRequest("http://localhost:8080");
        NewUrlResponse mockResponse = aNewUrlResponse();
        ShortenedUrlEntity mockShortenedUrlEntity = aShortenedUrlEntity();
        when(urlShortenerService.shortenUrl(anyString())).thenReturn(mockShortenedUrlEntity);

        mockMvc.perform(post("/newurl")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void redirectTest() throws Exception {
        String mockTargetUrl = "http://localhost:8080/abcd";
        when(urlShortenerService.getUrlById(anyString())).thenReturn(mockTargetUrl);

        mockMvc.perform(get("/abcd")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", mockTargetUrl));
    }

}
