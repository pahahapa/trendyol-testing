package com.trendyol.api.app_books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(value = {RequiredFieldIsBlankOrEmptyException.class})
    protected ResponseEntity<ApiError> handleRequiredFieldIsBlankOrEmpty(RequiredFieldIsBlankOrEmptyException ex, WebRequest request) {
        final String error = ex.getMessage();
        ApiError apiError = new ApiError(error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BookNotFoundException.class})
    protected ResponseEntity<Void> handleNotificationFailure(BookNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateAuthorAndTitleException.class})
    protected ResponseEntity<ApiError> handleDuplicateAuthorAndBookException(DuplicateAuthorAndTitleException ex, WebRequest request) {
        final String error = ex.getMessage();
        ApiError apiError = new ApiError(error);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    protected ResponseEntity<ApiError> handleDuplicateAuthorAndBookException(InternalServerErrorException ex, WebRequest request) {
        final String error = ex.getMessage();
        ApiError apiError = new ApiError(error);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
