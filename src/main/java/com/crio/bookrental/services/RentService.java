package com.crio.bookrental.services;

import com.crio.bookrental.config.jwt.JWTService;
import com.crio.bookrental.dtos.BookResponseDTO;
import com.crio.bookrental.dtos.RentResponseDTO;
import com.crio.bookrental.exceptions.BookNotFoundException;
import com.crio.bookrental.exceptions.MaximumBooksRentedException;
import com.crio.bookrental.exceptions.UserNotFoundException;
import com.crio.bookrental.exceptions.WrongUserException;
import com.crio.bookrental.models.Book;
import com.crio.bookrental.models.BookStatus;
import com.crio.bookrental.models.Rental;
import com.crio.bookrental.models.User;
import com.crio.bookrental.repositories.BookRepository;
import com.crio.bookrental.repositories.RentalRepository;
import com.crio.bookrental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final RentalRepository rentalRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(RentService.class);

    public RentService(BookRepository bookRepository, RentalRepository rentalRepository, UserRepository userRepository, JWTService jwtService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public List<BookResponseDTO> getAllAvailableBooks(){
        List<Book> books = bookRepository.findAll();
        List<BookResponseDTO> allBooks = new ArrayList<>();
        for(Book book: books){
            if(book.getAvailabilityStatus().equals(BookStatus.AVAILABLE)){
                allBooks.add(modelMapper.map(book, BookResponseDTO.class));
            }
        }
        logger.info("{} books are available", allBooks.size());
        return allBooks;
    }

    public RentResponseDTO rentBook(Long id){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Optional<User> user = userRepository.findById(jwtService.getUserIDFromJWT(token));
        Optional<Book> book = bookRepository.findById(id);
        int userCount = rentalRepository.countByRentedByAndReturnDateIsNull(user.get());
        if(userCount >= 2){
            throw new MaximumBooksRentedException("This user has reached maximum books to rent");
        }
        if(book.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        if(!book.get().getAvailabilityStatus().equals(BookStatus.AVAILABLE)){
            throw new BookNotFoundException("Book is not available");
        }
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        book.get().setAvailabilityStatus(BookStatus.RENTED);
        bookRepository.save(book.get());
        Rental rental = new Rental();
        rental.setRentedBy(user.get());
        rental.setBook(book.get());
        rental.setCreatedAt(LocalDate.now());
        logger.info("Book {} is rented by user {}", book.get().getId(), user.get().getId());
        return modelMapper.map(rentalRepository.save(rental), RentResponseDTO.class);
    }

    public void returnBook(Long bookId){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Optional<User> user = userRepository.findById(jwtService.getUserIDFromJWT(token));
        Optional<Book> book = bookRepository.findById(bookId);
        Rental rental = rentalRepository.findByBook(book.get());
        if(book.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        if(!book.get().getAvailabilityStatus().equals(BookStatus.RENTED)){
            throw new BookNotFoundException("Book is not rented");
        }
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        if(user.get().getId().equals(rental.getRentedBy().getId())){
            throw new WrongUserException("Wrong user");
        }
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);
        book.get().setAvailabilityStatus(BookStatus.AVAILABLE);
        bookRepository.save(book.get());
        logger.info("Book {} is returned by user {}", book.get().getId(), user.get().getId());
    }
}
