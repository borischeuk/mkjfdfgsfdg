package com.example.urlshortener.controller;

import com.example.urlshortener.config.property.ServiceConfigProperties;
import com.example.urlshortener.controller.dto.NewUrlRequest;
import com.example.urlshortener.controller.dto.NewUrlResponse;
import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import com.example.urlshortener.service.RateLimitService;
import com.example.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;
    private final RateLimitService rateLimitService;
    private final ServiceConfigProperties serviceFqdnProperties;

    @PostMapping(value = "/newurl")
    public NewUrlResponse newUrl(@RequestBody @Valid NewUrlRequest request, HttpServletRequest requestContext) {

        if(!rateLimitService.validateRateLimit(requestContext.getRemoteAddr())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exceed Rate Limit");
        }

        ShortenedUrlEntity newShortenedUrl = urlShortenerService.shortenUrl(request.getUrl());
        return NewUrlResponse.builder()
                .url(request.getUrl())
                .shortenUrl(serviceFqdnProperties.getFqdn() + "/" + newShortenedUrl.getId())
                .build();
    }

    @GetMapping(value = "/{urlId}")
    public void redirect(@PathVariable String urlId, HttpServletResponse response) {
        String targetUrl = urlShortenerService.getUrlById(urlId);
        response.setHeader("Location", targetUrl);
        response.setStatus(302);
    }

}
