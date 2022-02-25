package com.trendyol.api.app_books.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonDeserialize
@NoArgsConstructor
public class ApiError {

    private String error;

    public ApiError(final String error) {
        this.error = error;
    }
}