package com.example.urlshortener.repository;

import com.example.urlshortener.repository.entity.ShortenedUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrlEntity, String> {

//    ShortenedUrlEntity findById(String id);
    List<ShortenedUrlEntity> findByUrl(String url);

}
