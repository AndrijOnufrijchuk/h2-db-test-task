package com.example.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public long id;
    @Column (name = "name")
    public String name;
    @Column (name = "price")
    public int price;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable=false)
    public Category category;



    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="discount_id", referencedColumnName = "id",nullable=false)
    public Discount discount;

    public Product() {
    }

    public Product(String name, int price, Category category, Discount discount) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.discount = discount;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

}