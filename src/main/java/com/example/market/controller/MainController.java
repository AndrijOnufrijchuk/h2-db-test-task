package com.example.market.controller;


import com.example.market.entity.*;
import com.example.market.repository.*;
import com.example.market.service.BasketService;
import com.example.market.service.ProductService;
import com.example.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MainController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    DiscountRepo discountRepo;
    @Autowired
    BasketRepo basketRepo;
@Autowired
    BasketService basketService;
@Autowired
    ProductService productService;
@Autowired
    UserService userService;

    @GetMapping("/add_money/{userId}/{userName}/{value}")

    public String addMoney(@PathVariable("userId") int userId, @PathVariable("userName") String userName,
                           @PathVariable("value") int value) {

   return userService.addMoney(userId,userName,value);
    }


    @RequestMapping(value = "/pay/{productIds}/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String pay(@PathVariable List<Integer> productIds, @PathVariable("userId") int userId) {
      return basketService.buy(productIds,userId);
    }

    @RequestMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    private LinkedList<Product> getAllProducts() {
        return productService.getAllProducts();
    }



    @PostMapping("/create_user")
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@RequestBody User user) {
        userRepo.save(user);
        return user;
    }


    @PostMapping("/create_product")
    @ResponseStatus(HttpStatus.OK)
    public Object createProduct(@RequestBody Product product) {

        return productService.createProduct(product);
    }

    @PostMapping("/create_category")
    @ResponseStatus(HttpStatus.OK)
    public Category createCategory(@RequestBody Category category) {
        categoryRepo.save(category);
        return category;

    }
    @PostMapping("/create_basket")
    public Basket addProductToBasket(@RequestBody Basket basket) {
        basketRepo.save(basket);
        return basket;
    }


}
