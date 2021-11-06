package com.dataartschool.newsportal.exception;

public class NewsAlreadyExists extends RuntimeException {
    public NewsAlreadyExists(String message) {
        super(message);
    }
}
