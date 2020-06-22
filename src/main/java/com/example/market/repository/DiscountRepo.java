package com.example.market.repository;


import com.example.market.entity.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepo  extends CrudRepository<Discount, Long> {


}
