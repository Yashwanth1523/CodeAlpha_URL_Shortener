package com.codealpha.urlshortener.service;

import com.codealpha.urlshortener.dto.ShortUrlResponse;
import com.codealpha.urlshortener.model.ShortUrl;
import com.codealpha.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
public class UrlShortenerService {
    private final ShortUrlRepository repository;

    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrlResponse create(String rawUrl) {
        String normalizedUrl = validateAndNormalize(rawUrl);
        String code;
        do {
            code = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (repository.existsByShortCode(code));

        ShortUrl saved = repository.save(new ShortUrl(normalizedUrl, code));
        String shortUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{code}").buildAndExpand(code).toUriString();
        return new ShortUrlResponse(saved.getOriginalUrl(), saved.getShortCode(),
                shortUrl, saved.getCreatedAt());
    }

    public ShortUrl findByCode(String code) {
        return repository.findByShortCode(code)
                .orElseThrow(() -> new UrlNotFoundException("No URL found for code: " + code));
    }

    private String validateAndNormalize(String rawUrl) {
        String value = rawUrl.trim();
        try {
            URI uri = URI.create(value);
            String scheme = uri.getScheme();
            if (uri.getHost() == null || scheme == null ||
                    !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
                throw new IllegalArgumentException();
            }
            return uri.toASCIIString();
        } catch (RuntimeException ex) {
            throw new InvalidUrlException("Enter a valid URL beginning with http:// or https://");
        }
    }
}
