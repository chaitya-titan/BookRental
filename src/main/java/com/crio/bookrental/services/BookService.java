package com.crio.bookrental.services;

import com.crio.bookrental.config.jwt.JWTService;
import com.crio.bookrental.dtos.BookResponseDTO;
import com.crio.bookrental.dtos.CreateBookDTO;
import com.crio.bookrental.dtos.EditBookDTO;
import com.crio.bookrental.exceptions.BookNotFoundException;
import com.crio.bookrental.exceptions.UnauthorizedAccessException;
import com.crio.bookrental.models.Book;
import com.crio.bookrental.models.BookStatus;
import com.crio.bookrental.models.Role;
import com.crio.bookrental.models.User;
import com.crio.bookrental.repositories.BookRepository;
import com.crio.bookrental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository bookRepository, ModelMapper modelMapper,
                       JWTService jwtService, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public BookResponseDTO createBook(CreateBookDTO createBookDTO){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepository.findById(jwtService.getUserIDFromJWT(token)).get();
        if(!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        Book book = modelMapper.map(createBookDTO, Book.class);
        book.setCreatedAt(LocalDate.now());
        book.setAvailabilityStatus(BookStatus.AVAILABLE);
        book.setCreatedByUser(user);
        book = bookRepository.save(book);
        logger.info("Book {} created successfully", book.getId());
        return modelMapper.map(book, BookResponseDTO.class);
    }

    public BookResponseDTO updateBook(Long id, EditBookDTO editBookDTO){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepository.findById(jwtService.getUserIDFromJWT(token)).get();
        if(!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        Book book = bookRepository.findById(id).get();
        modelMapper.map(editBookDTO, book);
        book = bookRepository.save(book);
        logger.info("Book {} updated successfully", book.getId());
        return modelMapper.map(book, BookResponseDTO.class);
    }

    public void deleteBook(Long id){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepository.findById(jwtService.getUserIDFromJWT(token)).get();
        if(!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.delete(book.get());
        logger.info("Book {} deleted successfully", book.get().getId());
    }

    public List<BookResponseDTO> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        List<BookResponseDTO> allBooks = new ArrayList<>();
        for(Book book: books){
            allBooks.add(modelMapper.map(book, BookResponseDTO.class));
        }
        logger.info("{} books are available", allBooks.size());
        return allBooks;
    }
}
