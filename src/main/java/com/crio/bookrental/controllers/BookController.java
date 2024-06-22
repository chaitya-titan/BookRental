package com.crio.bookrental.controllers;

import com.crio.bookrental.dtos.BookResponseDTO;
import com.crio.bookrental.dtos.CreateBookDTO;
import com.crio.bookrental.dtos.EditBookDTO;
import com.crio.bookrental.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> bookResponseDTOs = bookService.getAllBooks();
        return ResponseEntity.ok().body(bookResponseDTOs);
    }

    @PostMapping("/create")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody CreateBookDTO createBookDTO) {
        BookResponseDTO bookResponseDTO = bookService.createBook(createBookDTO);
        return ResponseEntity.created(null).body(bookResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody EditBookDTO editBookDTO) {
        BookResponseDTO bookResponseDTO = bookService.updateBook(id, editBookDTO);
        return ResponseEntity.ok().body(bookResponseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
