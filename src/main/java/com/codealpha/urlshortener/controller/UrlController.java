package com.codealpha.urlshortener.controller;

import com.codealpha.urlshortener.dto.CreateUrlRequest;
import com.codealpha.urlshortener.dto.ShortUrlResponse;
import com.codealpha.urlshortener.model.ShortUrl;
import com.codealpha.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlController {
    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/api/urls")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrlResponse create(@Valid @RequestBody CreateUrlRequest request) {
        return service.create(request.url());
    }

    @GetMapping("/api/urls/{code}")
    public ShortUrlResponse getDetails(@PathVariable String code) {
        ShortUrl url = service.findByCode(code);
        String shortUrl = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{code}").buildAndExpand(code).toUriString();
        return new ShortUrlResponse(url.getOriginalUrl(), url.getShortCode(),
                shortUrl, url.getCreatedAt());
    }

    @GetMapping("/{code}")
    public RedirectView redirect(@PathVariable String code) {
        ShortUrl url = service.findByCode(code);
        RedirectView view = new RedirectView(url.getOriginalUrl());
        view.setStatusCode(HttpStatus.FOUND);
        view.setExposeModelAttributes(false);
        return view;
    }
    @GetMapping("/")
    public String home() {
        return "URL Shortener API is running!";
    }
}
