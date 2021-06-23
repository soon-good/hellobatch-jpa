package io.study.batch.hellobatch_jpa;

import io.study.batch.hellobatch_jpa.shop.book.BookService;
import io.study.batch.hellobatch_jpa.shop.book.dto.BookDto;
import io.study.batch.hellobatch_jpa.shop.book.functions.DtoConsumers;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.function.Consumer;

@Profile("test-rabbitmq-postgresql")
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class HellobatchJpaApplication {

	@Autowired
	private BookService bookService;

	private List<BookDto> books;

	public static void main(String[] args) {
		SpringApplication.run(HellobatchJpaApplication.class, args);
	}

	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	public void printBooks(){
		Consumer<List<BookDto>> listConsumer = DtoConsumers.printBooks();
		listConsumer.accept(books);
		System.out.println();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationLoaded(){
		books = bookService.listAllBook();
	}
}
