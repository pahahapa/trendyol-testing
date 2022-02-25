package com.trendyol.api.app_books.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@JsonSerialize
@EqualsAndHashCode(of = {"id"})
public class BookDto {
    private final long id;
    private final String author;
    private final String title;
}
