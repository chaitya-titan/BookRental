package com.crio.bookrental.repositories;

import com.crio.bookrental.models.Book;
import com.crio.bookrental.models.Rental;
import com.crio.bookrental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    int countByRentedByAndReturnDateIsNull(User user);
    Rental findByBook(Book book);
}
