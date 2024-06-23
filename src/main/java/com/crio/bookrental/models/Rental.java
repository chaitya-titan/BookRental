package com.crio.bookrental.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rentals")
@EqualsAndHashCode(callSuper = true)
public class Rental extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User rentedBy;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate returnDate;
}
