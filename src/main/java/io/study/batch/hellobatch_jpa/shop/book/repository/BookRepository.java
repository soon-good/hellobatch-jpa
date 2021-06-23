package io.study.batch.hellobatch_jpa.shop.book.repository;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
