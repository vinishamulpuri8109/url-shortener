package com.example.url_shortener.controller;

import com.example.url_shortener.dto.UrlRequest;
import com.example.url_shortener.dto.UrlResponse;
import com.example.url_shortener.entity.UrlMapping;
import com.example.url_shortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Optional;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest urlRequest) {
        try {
            String shortUrl = urlShortenerService.shortenUrl(urlRequest.getUrl());
            String shortCode = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);

            UrlResponse response = new UrlResponse(urlRequest.getUrl(), shortUrl, shortCode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/r/{shortCode}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        if (originalUrl.isPresent()) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalUrl.get());
            return redirectView;
        } else {
            // Redirect to error page
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/error/404");
            return redirectView;
        }
    }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<UrlMapping> getUrlStats(@PathVariable String shortCode) {
        Optional<UrlMapping> urlMapping = urlShortenerService.getUrlStats(shortCode);
        return urlMapping.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("URL Shortener Service is running!");
    }
}