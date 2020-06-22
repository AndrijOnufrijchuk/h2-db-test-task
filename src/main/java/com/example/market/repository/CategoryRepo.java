package com.example.market.repository;

import com.example.market.entity.Category;
import com.example.market.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends CrudRepository<Category, Long> {
}
