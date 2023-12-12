package com.jpabook.jpashop.main.domain.dto.request;

import com.jpabook.jpashop.main.domain.entity.item.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookForm {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public BookForm(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }


    public Book createBook() {
        return new Book(id, name, price, stockQuantity, author, isbn);
    }
}
