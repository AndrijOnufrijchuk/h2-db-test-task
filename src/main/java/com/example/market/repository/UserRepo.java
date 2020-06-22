package com.example.market.repository;

import com.example.market.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface UserRepo extends CrudRepository<User, Long> {

}
