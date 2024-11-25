package com.shiraku.javacodespring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Book title is required")
    private String title;

    private String genre;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
