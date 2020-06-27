package com.example.market.service;

import com.example.market.entity.Basket;
import com.example.market.repository.BasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    private BasketRepo basketRepo;


public List<Basket> findAllByUserId(long userId){
List<Basket> basketList = new ArrayList<>();

 List<Basket> baskets = (List<Basket>) basketRepo.findAll();
for(Basket basket:baskets){
    if(basket.getUser().getId()==userId  ){
        basketList.add(basket);
        System.out.println("basket added");
    }

}

return  basketList;
}

}
