package com.jpabook.jpashop.domain.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("B")
@NoArgsConstructor
public class Book extends Item {
    private String author;
    private String isbn;

    public Book(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        super(id, name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public void changeAuthorAndIsbn(Book param) {
        this.author = param.getAuthor();
        this.isbn = param.getIsbn();
    }
}
