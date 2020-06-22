package com.example.market.repository;

import com.example.market.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo  extends CrudRepository<Product, Long> {

}
