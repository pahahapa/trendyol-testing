package com.trendyol.api.app_books.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"author", "title"})})
@EqualsAndHashCode(of = {"id"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Column(name = "author", nullable = false, length = 200)
    private String author;

    @Basic
    @Column(name = "title", nullable = false, length = 200)
    private String title;
}
