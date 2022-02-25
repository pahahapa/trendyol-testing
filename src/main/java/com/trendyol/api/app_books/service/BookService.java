package com.trendyol.api.app_books.service;

import com.trendyol.api.app_books.dto.AuthorAndTitleDto;
import com.trendyol.api.app_books.dto.BookDto;

import java.util.Set;


public interface BookService {
    Set<BookDto> getAll();

    BookDto storeBook(AuthorAndTitleDto authorAndBookDto);

    BookDto findById(Long bookId);

    void deleteAllBooks();
}
