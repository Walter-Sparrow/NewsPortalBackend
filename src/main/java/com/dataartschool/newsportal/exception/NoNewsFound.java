package com.dataartschool.newsportal.exception;

public class NoNewsFound extends RuntimeException {
    public NoNewsFound(String message) {
        super(message);
    }
}
