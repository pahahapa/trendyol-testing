package com.trendyol.api.app_books.exception;

public class RequiredFieldIsBlankOrEmptyException extends IllegalArgumentException {
    public RequiredFieldIsBlankOrEmptyException(String message) {
        super(message);
    }

}
