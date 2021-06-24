package io.study.batch.hellobatch_jpa.shop.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString(of = {"id", "name"})
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    public Book(){}

    public Book(String name, Integer price){
        this.name = name;
        this.price = price;
    }

    public Book(Long id, String name, Integer price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
