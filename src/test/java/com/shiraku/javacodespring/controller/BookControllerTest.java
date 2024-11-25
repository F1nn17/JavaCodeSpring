package com.shiraku.javacodespring.controller;
import com.shiraku.javacodespring.model.Author;
import com.shiraku.javacodespring.model.Book;
import com.shiraku.javacodespring.repository.AuthorRepository;
import com.shiraku.javacodespring.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookController bookController;

    private Book book1;
    private Book book2;
    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author 1");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setGenre("Fiction");
        book1.setYear(2021);
        book1.setAuthor(author);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setGenre("Non-Fiction");
        book2.setYear(2022);
        book2.setAuthor(author);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 10), books.size());

        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(bookPage);

        mockMvc.perform(get("/api/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.content[1].title").value("Book 2"));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.genre").value("Fiction"));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);
        when(authorRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book 1\",\"genre\":\"Fiction\",\"year\":2021,\"author\":{\"id\":1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.genre").value("Fiction"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");
        updatedBook.setGenre("Updated Genre");
        updatedBook.setYear(2023);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Book\",\"genre\":\"Updated Genre\",\"year\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.genre").value("Updated Genre"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(bookRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/books/999"))
                .andExpect(status().isNotFound());
    }
}
