package com.example.HighwayManager.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateException extends BaseException {
    public IllegalStateException(String message) {
        super(message, "ILLEGAL_STATE", HttpStatus.BAD_REQUEST);
    }
}
