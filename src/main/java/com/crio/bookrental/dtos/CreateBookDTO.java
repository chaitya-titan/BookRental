package com.crio.bookrental.dtos;

import lombok.Data;

@Data
public class CreateBookDTO {
    String bookTitle;
    String author;
    String genre;
}
