package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {
}
