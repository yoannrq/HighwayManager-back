package com.example.HighwayManager.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends BaseException {
    public IllegalArgumentException(String message) {
        super(message, "ILLEGAL_ARGUMENT", HttpStatus.FORBIDDEN);
    }
}