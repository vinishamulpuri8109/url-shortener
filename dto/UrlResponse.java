package com.example.url_shortener.dto;

public class UrlResponse {
    private String originalUrl;
    private String shortUrl;
    private String shortCode;

    // Constructors
    public UrlResponse() {}

    public UrlResponse(String originalUrl, String shortUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
    }

    // Getters and Setters
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
}