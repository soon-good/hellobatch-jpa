package io.study.batch.hellobatch_jpa.etc;

import io.study.batch.hellobatch_jpa.shop.book.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class FunctionsTest {

    @Test
    @DisplayName("리스트_무한반복_테스트")
    public void 리스트_무한반복_테스트(){
        Consumer<List<Book>> consumer = (list)->{
            while(true){
                for(Book b : list){
                    System.out.println(b);
                }
            }
        };

        List<Book> books = Arrays.asList(
                new Book(1L, "시인은 저녁에 감성이 돋는다", 200),
                new Book(2L, "오늘은 이만 쉴께요", 200),
                new Book(3L, "사업왕 심길후", 200),
                new Book(4L, "싸이월드 일촌 1000 명 만들기", 200),
                new Book(5L, "한국 부동산 지도 - 빠짐없이 모두 올랐다!!! 살데가 없다.", 200)
        );

        consumer.accept(books);
    }
}
