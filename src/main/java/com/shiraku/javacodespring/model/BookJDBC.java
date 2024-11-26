package com.shiraku.javacodespring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
@Data
public class BookJDBC {
    @Id
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;

    public BookJDBC(String title, String author, Integer publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
