package com.shiraku.javacodespring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiraku.javacodespring.model.BookJDBC;
import com.shiraku.javacodespring.service.BookJDBCService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookJDBCController.class)
public class BookJDBCControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockitoBean
    private BookJDBCService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        BookJDBC book = new BookJDBC("Book Title", "Author Name", 2023);
        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(get("/api/jdbcbooks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book Title"))
                .andExpect(jsonPath("$[0].author").value("Author Name"))
                .andExpect(jsonPath("$[0].publicationYear").value(2023));
    }

    @Test
    public void testGetBookById() throws Exception {
        BookJDBC book = new BookJDBC("Book Title", "Author Name", 2023);
        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/jdbcbooks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.author").value("Author Name"))
                .andExpect(jsonPath("$.publicationYear").value(2023));
    }

    @Test
    public void testAddBook() throws Exception {
        BookJDBC book = new BookJDBC("New Book", "New Author", 2023);

        mockMvc.perform(post("/api/jdbcbooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        Mockito.verify(bookService).addBook(any(BookJDBC.class));
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookJDBC book = new BookJDBC("Updated Book", "Updated Author", 2023);

        mockMvc.perform(put("/api/jdbcbooks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        Mockito.verify(bookService).updateBook(eq(1L), any(BookJDBC.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/jdbcbooks/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(bookService).deleteBook(1L);
    }
}
