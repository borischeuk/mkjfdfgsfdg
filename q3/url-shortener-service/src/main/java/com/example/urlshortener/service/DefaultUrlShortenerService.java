package com.example.urlshortener.service;

import com.example.urlshortener.repository.ShortenedUrlRepository;
import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUrlShortenerService implements UrlShortenerService {

    private final ShortenedUrlRepository shortenedUrlRepository;

    public ShortenedUrlEntity shortenUrl(String url) {
        List<ShortenedUrlEntity> matchedShortenedUrlList = shortenedUrlRepository.findByUrl(url);
        if (matchedShortenedUrlList.isEmpty()) {
            ShortenedUrlEntity newShortenedUrlEntity = ShortenedUrlEntity.builder()
                    .id(generateRandomUrlId())
                    .url(url)
                    .build();
            return shortenedUrlRepository.save(newShortenedUrlEntity);
        } else {
            return matchedShortenedUrlList
                    .stream()
                    .findFirst()
                    .get();
        }
    }

    public String getUrlById(String urlId) {
        ShortenedUrlEntity shortenedUrlEntity = shortenedUrlRepository.findById(urlId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found"));

        return shortenedUrlEntity.getUrl();
    }

    private String generateRandomUrlId() {
        while (true) {
            String urlId = RandomStringUtils.randomAlphanumeric(9);
            if (!shortenedUrlRepository.existsById(urlId)) {
                return urlId;
            }
        }
    }

}
