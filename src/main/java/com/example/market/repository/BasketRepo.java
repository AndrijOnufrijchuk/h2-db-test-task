package com.example.market.repository;

import com.example.market.entity.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepo extends CrudRepository<Basket, Long> {

    List<Basket> findAllByUserId(long userId);

}
