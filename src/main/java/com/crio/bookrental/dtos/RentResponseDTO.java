package com.crio.bookrental.dtos;

import com.crio.bookrental.models.Book;
import com.crio.bookrental.models.User;
import lombok.Data;

@Data
public class RentResponseDTO {
    Long id;
    Book book;
    User rentedBy;
}
