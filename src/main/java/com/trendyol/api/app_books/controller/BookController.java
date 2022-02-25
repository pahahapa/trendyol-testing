package com.trendyol.api.app_books.controller;

import com.trendyol.api.app_books.dto.AuthorAndTitleDto;
import com.trendyol.api.app_books.dto.BookDto;
import com.trendyol.api.app_books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<Set<BookDto>> storedBooks() {
        Set<BookDto> allStoredBooks = bookService.getAll();
        return new ResponseEntity<>(allStoredBooks, HttpStatus.OK);
    }

    @PutMapping("/books")
    public ResponseEntity<BookDto> storeNewBook(@RequestBody AuthorAndTitleDto authorAndBookDto) {
        final BookDto newStoredBook = bookService.storeBook(authorAndBookDto);
        return new ResponseEntity<>(newStoredBook, HttpStatus.OK);
    }

    @GetMapping("/books/{book_id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable(name = "book_id") Long bookId) {
        final BookDto newStoredBook = bookService.findById(bookId);
        return new ResponseEntity<>(newStoredBook, HttpStatus.OK);
    }

    @DeleteMapping("/books")
    public ResponseEntity<Void> deleteBooks() {
        bookService.deleteAllBooks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
