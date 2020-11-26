package com.example.urlshortener.service;

import com.example.urlshortener.repository.entity.ShortenedUrlEntity;

public interface UrlShortenerService {

    ShortenedUrlEntity shortenUrl(String url);

    String getUrlById(String urlId);

}
