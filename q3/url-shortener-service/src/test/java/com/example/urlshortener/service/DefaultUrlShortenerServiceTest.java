package com.example.urlshortener.service;

import com.example.urlshortener.repository.ShortenedUrlRepository;
import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.example.urlshortener.prototype.repository.entity.ShortenedUrlEntityPrototypes.aShortenedUrlEntity;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultUrlShortenerServiceTest {

    private UrlShortenerService urlShortenerService;

    private ShortenedUrlRepository shortenedUrlRepository;

    @BeforeEach
    void setup() {
        shortenedUrlRepository = mock(ShortenedUrlRepository.class);

        urlShortenerService = new DefaultUrlShortenerService(shortenedUrlRepository);
    }

    @Test
    void shortenUrlWhenUrlExist() {
        String url = "http://localhost:8080";
        ShortenedUrlEntity mockResult = aShortenedUrlEntity();
        when(shortenedUrlRepository.findByUrl(anyString())).thenReturn(singletonList(mockResult));

        ShortenedUrlEntity actualResult = urlShortenerService.shortenUrl(url);

        assertThat(actualResult).isEqualTo(mockResult);
    }

    @Test
    void shortenUrlCreateNewUrl() {
        String url = "http://localhost:8080";
        ShortenedUrlEntity mockResult = aShortenedUrlEntity();
        when(shortenedUrlRepository.findByUrl(anyString())).thenReturn(emptyList());
        when(shortenedUrlRepository.save(any())).thenReturn(mockResult);
        when(shortenedUrlRepository.existsById(anyString())).thenReturn(false);

        ShortenedUrlEntity actualResult = urlShortenerService.shortenUrl(url);

        verify(shortenedUrlRepository, times(1)).save(any());
        verify(shortenedUrlRepository, times(1)).existsById(any());
        assertThat(actualResult).isEqualTo(mockResult);
    }

    @Test
    void getUrlByIdSuccessfully() {
        String urlId = "1234";
        ShortenedUrlEntity mockResult = aShortenedUrlEntity();
        when(shortenedUrlRepository.findById(urlId)).thenReturn(Optional.of(mockResult));

        String actualResult = urlShortenerService.getUrlById(urlId);

        assertThat(actualResult).isEqualTo(mockResult.getUrl());
    }

    @Test
    void getUrlByIdFailed() {
        String urlId = "1234";
        when(shortenedUrlRepository.findById(urlId)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> urlShortenerService.getUrlById(urlId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("404 NOT_FOUND \"Page not found\"");
    }

}
