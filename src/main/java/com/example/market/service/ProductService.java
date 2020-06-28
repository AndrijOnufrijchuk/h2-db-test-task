package com.example.market.service;

import com.example.market.entity.Discount;
import com.example.market.entity.Product;
import com.example.market.repository.DiscountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.market.repository.ProductRepo;
import java.util.LinkedList;


@Service

public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    DiscountRepo discountRepo;

    public LinkedList<Product> getAllProducts() {
        LinkedList<Product> products = new LinkedList<>();
        productRepo.findAll().forEach(products::add);
        return products;
    }

    public Object createProduct(Product product) {
        if (product.getPercentOfDiscount() < 0 || product.getPercentOfDiscount() >= 100) {
            return HttpStatus.NOT_ACCEPTABLE.getReasonPhrase();
        }
        productRepo.save(product);
        discountRepo.save(new Discount(product));
        return product;
    }


}
