package com.codealpha.urlshortener.service;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message) { super(message); }
}
