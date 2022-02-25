package com.trendyol.api.app_books.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String errMsg) {
        super(errMsg);
    }
}
