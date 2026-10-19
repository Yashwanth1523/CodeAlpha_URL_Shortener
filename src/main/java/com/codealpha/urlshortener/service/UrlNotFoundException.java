package com.codealpha.urlshortener.service;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) { super(message); }
}
