package io.study.batch.hellobatch_jpa.shop.book.functions;

import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;

import java.util.List;
import java.util.function.Consumer;

public class DtoConsumers {

    public static Consumer<List<BookDto>> printBooks(){
        return (books)->{
            for(BookDto book: books){
                System.out.println(book);
            }
        };
    }
}
