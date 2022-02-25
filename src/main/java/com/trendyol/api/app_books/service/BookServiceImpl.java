package com.trendyol.api.app_books.service;

import com.trendyol.api.app_books.exception.*;
import com.trendyol.api.app_books.utils.Randomizer;
import com.trendyol.api.app_books.dto.AuthorAndTitleDto;
import com.trendyol.api.app_books.dto.BookDto;
import com.trendyol.api.app_books.entity.BookEntity;
import com.trendyol.api.app_books.repository.BookRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<BookDto> getAll() {
        try {
            Randomizer.shuffle();
            final List<BookEntity> bookEntityList = bookRepository.findAll();
            return bookEntityList.stream().map(this::mapToDto)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (RandomException randomException) {
            throw new InternalServerErrorException(randomException.getMessage());
        }
    }

    @Override
    public BookDto storeBook(final AuthorAndTitleDto authorAndTitleDto) {
        final String author = authorAndTitleDto.getAuthor();
        if (null == author) {
            throw new RequiredFieldIsBlankOrEmptyException("Field 'author' is required");
        }
        if (Strings.isEmpty(author)) {
            throw new RequiredFieldIsBlankOrEmptyException("Field 'author' cannot be empty");
        }
        final String title = authorAndTitleDto.getTitle();
        if (null == title) {
            throw new RequiredFieldIsBlankOrEmptyException("Field 'title' is required");
        }
        if (Strings.isEmpty(title)) {
            throw new RequiredFieldIsBlankOrEmptyException("Field 'title' cannot be empty");
        }
        try {
            Randomizer.shuffle();
            final BookEntity newBook = bookRepository.save(BookEntity.builder()
                    .author(author)
                    .title(title)
                    .build());
            return mapToDto(newBook);
        } catch (RandomException randomException) {
            throw new InternalServerErrorException(randomException.getMessage());
        } catch (Exception e) {
            final String errorResponse = "Another book with similar title and author already exists";
            throw new DuplicateAuthorAndTitleException(errorResponse);
        }
    }

    @Override
    public BookDto findById(Long bookId) {
        try {
            Randomizer.shuffle();
            final BookEntity foundBook = bookRepository.findById(bookId)
                    .orElseThrow(EntityNotFoundException::new);
            return mapToDto(foundBook);
        } catch (RandomException randomException) {
            throw new InternalServerErrorException(randomException.getMessage());
        } catch (EntityNotFoundException e) {
            throw new BookNotFoundException();
        }

    }

    /**
     * For purposes clearing the database in the context of final operations after each test.
     * The testing API is reset before each of your test cases. You don't have to worry
     * about it. But I took care of this due to some features of the application
     */
    @Override
    public void deleteAllBooks() {
        try {
            Randomizer.shuffle();
            bookRepository.deleteAll();
        } catch (RandomException randomException) {
            throw new InternalServerErrorException(randomException.getMessage());
        }
    }

    private BookDto mapToDto(BookEntity bookEntity) {
        return BookDto.builder()
                .id(bookEntity.getId())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .build();
    }
}
