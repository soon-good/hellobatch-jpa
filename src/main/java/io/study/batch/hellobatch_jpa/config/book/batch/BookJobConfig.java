package io.study.batch.hellobatch_jpa.config.book.batch;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import io.study.batch.hellobatch_jpa.shop.book.BookService;
import io.study.batch.hellobatch_jpa.shop.book.repository.BookRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@Configuration
public class BookJobConfig {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookItemProcessor bookItemProcessor;
    private final EntityManagerFactory entityManagerFactory;
//    private final RabbitTemplate rabbitTemplate;

    public BookJobConfig(JobBuilderFactory jobBuilderFactory,
                         StepBuilderFactory stepBuilderFactory,
                         BookItemProcessor processor,
                         EntityManagerFactory entityManagerFactory){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.bookItemProcessor = processor;
        this.entityManagerFactory = entityManagerFactory;
//        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    @JobScope
    public Job bookPrintJob(@Qualifier("bookPrintStep")Step bookPrintStep){
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));

        JobParameters reqDate = new JobParametersBuilder().addDate("reqDate", Date.from(now.atZone(ZoneId.systemDefault()).toInstant())).toJobParameters();

        return jobBuilderFactory.get("bookPrintJob")
                .preventRestart()
                .start(bookPrintStep)
                .build();
    }

    @Bean
    @StepScope
    public Step bookPrintStep(@Value("#{jobParameters[requestDate]}") String requestDate,
                              @Qualifier("bookSelectAllReader") ListItemReader bookSelectAllReader,
                              @Qualifier("bookSelectAllWriter") JpaItemWriter bookSelectAllWriter){
        return stepBuilderFactory.get("bookPrintStepBuilder")
                .<Book, Book>chunk(10)
                .reader(bookSelectAllReader)
                .processor(bookItemProcessor)
                .writer(bookSelectAllWriter)
                .build();
    }

    @Bean(name = "bookSelectAllReader")
    public ListItemReader<Book> bookSelectAllReader(){
        List<Book> list = bookRepository.findAll();
        ListItemReader<Book> bookListReader = new ListItemReader<>(list);
        return bookListReader;
    }

    @Bean(name = "bookSelectAllWriter")
    public JpaItemWriter<Book> bookSelectAllWriter(){
        JpaItemWriter<Book> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

//    @Bean(name = "bookAmqpReader")
//    public ItemReader<Book> bookItemReader(){
//        return new AmqpItemReader<>(rabbitTemplate);
//    }
//
//    @Bean(name = "bookAmqpJpaWriter")
//    public JpaItemWriter<Book> bookAmqpJpaWriter(){
//        JpaItemWriter<Book> jpaItemWriter = new JpaItemWriter<>();
//        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
//        return jpaItemWriter;
//    }

}
