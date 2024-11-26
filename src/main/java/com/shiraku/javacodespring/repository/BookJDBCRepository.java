package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.BookJDBC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookJDBC> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", (rs, rowNum) -> new BookJDBC(
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("publication_year")
        ));
    }

    public BookJDBC findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM books WHERE id = ?",
                (rs, rowNum) -> new BookJDBC(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publication_year")
                ),
                id
        );
    }

    public void save(BookJDBC book) {
        jdbcTemplate.update("INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear()
        );
    }

    public void update(Long id, BookJDBC book) {
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?",
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                id
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }
}
