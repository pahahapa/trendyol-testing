package com.trendyol.api.app_books.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@JsonDeserialize(builder = AuthorAndTitleDto.AuthorAndTitleDtoBuilder.class)
public class AuthorAndTitleDto {
    private String author;
    private String title;
}
