package com.crio.bookrental.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "books")
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseModel{
    @Column(name = "title")
    private String bookTitle;
    private String author;
    private String genre;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private BookStatus availabilityStatus;
}
