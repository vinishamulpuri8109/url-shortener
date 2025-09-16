package com.example.url_shortener.dto;
import jakarta.validation.constraints.NotBlank;
public class UrlRequest {
    @NotBlank(message = "URL is required")
    private String url;

    // Getters and Setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
