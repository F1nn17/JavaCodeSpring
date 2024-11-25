package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
