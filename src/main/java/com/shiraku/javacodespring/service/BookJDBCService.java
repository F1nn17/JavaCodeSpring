package com.shiraku.javacodespring.service;

import com.shiraku.javacodespring.model.BookJDBC;
import com.shiraku.javacodespring.repository.BookJDBCRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookJDBCService {
    private final BookJDBCRepository bookRepository;

    public BookJDBCService(BookJDBCRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookJDBC> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookJDBC getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void addBook(BookJDBC book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, BookJDBC book) {
        bookRepository.update(id, book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
}
