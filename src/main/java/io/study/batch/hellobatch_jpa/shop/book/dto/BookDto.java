package io.study.batch.hellobatch_jpa.shop.book.dto;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class BookDto {

    private String name;

    public BookDto(Book book){
        this.name = book.getName();
    }
}
