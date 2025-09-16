package com.example.url_shortener.service;

import com.example.url_shortener.entity.UrlMapping;
import com.example.url_shortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final Random random = new Random();

    @Value("${urlshortener.code.length:6}")
    private int codeLength;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String shortenUrl(String originalUrl) {
        // Check if URL already exists
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByOriginalUrl(originalUrl);
        if (existingMapping.isPresent()) {
            return generateShortUrl(existingMapping.get().getShortCode());
        }

        // Generate unique short code
        String shortCode;
        do {
            shortCode = generateRandomCode();
        } while (urlMappingRepository.existsByShortCode(shortCode));

        // Save to database
        UrlMapping urlMapping = new UrlMapping(originalUrl, shortCode);
        urlMappingRepository.save(urlMapping);

        return generateShortUrl(shortCode);
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<UrlMapping> urlMapping = urlMappingRepository.findByShortCode(shortCode);
        urlMapping.ifPresent(mapping -> {
            mapping.incrementClickCount();
            urlMappingRepository.save(mapping);
        });
        return urlMapping.map(UrlMapping::getOriginalUrl);
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }

        return code.toString();
    }

    private String generateShortUrl(String shortCode) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/r/")
                .path(shortCode)
                .toUriString();
    }

    public Optional<UrlMapping> getUrlStats(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode);
    }
}