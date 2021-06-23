package io.study.batch.hellobatch_jpa.shop.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@ToString(of = {"id", "name"})
public class Book {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    public Book(){}

    public Book(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Book(Long id, String name, Integer price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
