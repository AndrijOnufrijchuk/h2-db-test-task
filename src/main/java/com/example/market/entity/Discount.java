package com.example.market.entity;

import javax.persistence.*;

@Entity
@Table(name = "Discounts")
public class Discount {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public long id;
    public double percentOfDiscount;
    @OneToOne(mappedBy = "discount")
    private Product product;

    public Discount() {
    }

    public Discount(double percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPercentOfDiscount() {
        return percentOfDiscount;
    }

    public void setPercentOfDiscount(double percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
