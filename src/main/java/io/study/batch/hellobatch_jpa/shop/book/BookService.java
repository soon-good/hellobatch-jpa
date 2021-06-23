package io.study.batch.hellobatch_jpa.shop.book;

import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> listAllBook();
}
