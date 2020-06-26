package com.example.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column (name = "name")
    private String name;
    @Column (name = "price")
    private int price;
    @Column (name = "quantity")
    private int quantity;

    @Column (name="percentOfDiscount")
    public int percentOfDiscount;
    @OneToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable=false)
    public Category category;



    public Product() {
    }

    public Product(String name, int price,int quantity, Category category, int percentOfDiscount) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.percentOfDiscount = percentOfDiscount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPercentOfDiscount() {
        return percentOfDiscount;
    }

    public void setPercentOfDiscount(int percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }
}
