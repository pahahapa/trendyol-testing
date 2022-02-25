package com.trendyol.api.app_books.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateAuthorAndTitleException extends DuplicateKeyException {
    public DuplicateAuthorAndTitleException(String msg) {
        super(msg);
    }
}
