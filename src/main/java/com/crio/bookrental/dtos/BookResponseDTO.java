package com.crio.bookrental.dtos;

import com.crio.bookrental.models.BookStatus;
import com.crio.bookrental.models.User;
import lombok.Data;

@Data
public class BookResponseDTO {
    Long id;
    String title;
    String author;
    String genre;
    BookStatus availabilityStatus;
    User createdByUser;
}
