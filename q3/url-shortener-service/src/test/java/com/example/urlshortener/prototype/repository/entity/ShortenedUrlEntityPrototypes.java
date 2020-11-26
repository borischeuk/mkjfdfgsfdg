package com.example.urlshortener.prototype.repository.entity;

import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class ShortenedUrlEntityPrototypes {

    public static ShortenedUrlEntity aShortenedUrlEntity() {
        return ShortenedUrlEntity.builder()
                .id("abcd")
                .url("http://localhost:8080")
                .build();
    }

}
