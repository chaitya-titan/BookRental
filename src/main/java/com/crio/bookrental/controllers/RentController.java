package com.crio.bookrental.controllers;

import com.crio.bookrental.dtos.BookResponseDTO;
import com.crio.bookrental.dtos.RentResponseDTO;
import com.crio.bookrental.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping("")
    public ResponseEntity<List<BookResponseDTO>> getAllAvailableBooks() {
        List<BookResponseDTO> bookResponseDTOs = rentService.getAllAvailableBooks();
        return ResponseEntity.ok().body(bookResponseDTOs);
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<RentResponseDTO> rentBook(@PathVariable Long bookId) {
        RentResponseDTO rentResponseDTO = rentService.rentBook(bookId);
        return ResponseEntity.ok().body(rentResponseDTO);
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        rentService.returnBook(bookId);
        return ResponseEntity.ok().body("Book returned successfully");
    }
}
