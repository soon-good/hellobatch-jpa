package io.study.batch.hellobatch_jpa.shop.book.dto;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@EqualsAndHashCode(of = {"name", "price"})
public class BookDto {

    private String name;

    private Integer price;

    public BookDto(Book book){
        this.name = book.getName();
        this.price = book.getPrice();
    }
}
