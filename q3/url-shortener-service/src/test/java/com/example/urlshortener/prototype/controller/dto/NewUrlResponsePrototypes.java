package com.example.urlshortener.prototype.controller.dto;

import com.example.urlshortener.controller.dto.NewUrlResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class NewUrlResponsePrototypes {

    public static NewUrlResponse aNewUrlResponse() {
        return NewUrlResponse.builder()
                .shortenUrl("http://localhost:8080/abcd")
                .url("http://localhost:8080")
                .build();
    }

}
