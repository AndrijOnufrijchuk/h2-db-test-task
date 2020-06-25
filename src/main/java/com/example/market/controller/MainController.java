package com.example.market.controller;


import com.example.market.entity.Category;
import com.example.market.entity.Discount;
import com.example.market.entity.Product;
import com.example.market.entity.User;
import com.example.market.repository.CategoryRepo;
import com.example.market.repository.DiscountRepo;
import com.example.market.repository.ProductRepo;
import com.example.market.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/")

    public String homePage() {

        return "hi Andrusha";
    }


    @GetMapping("/add_money/{userId}/{userName}/{value}")

    public String addMoney(@PathVariable("userId") int userId, @PathVariable("userName") String userName,
                           @PathVariable("value") int value) {
        if (userRepo.findById((long) userId).isPresent()) {
            Long l = (long) userId;
            User user = userRepo.findById(l).get();
            if (user.getName().equals(userName)) {
                user.setBalance(user.getBalance() + value);
                userRepo.save(user);
                return String.valueOf(userRepo.findById(l).get().getBalance());
            }
            return "Invalid username for this id";
        }

        return "Invalid Id";
    }

    @RequestMapping(value ="/list")

    @ResponseStatus(HttpStatus.OK)
    private  LinkedList<Product> getAllProducts() {
        LinkedList<Product> products = new LinkedList<>();
        productRepo.findAll().forEach(products::add);
      return products;
    }
    @RequestMapping(value ="/default")
            private String onInitialized() {
        Category category = new Category("Meat");
        Discount discount = new Discount(12.2);
        User user = new User("Andrusha", 500);
        Product product = new Product("Pork", 150, 30, category, discount);

        categoryRepo.save(category);
        discountRepo.save(discount);
        userRepo.save(user);
        productRepo.save(product);
        return HttpStatus.OK.getReasonPhrase();
    }

    @PostMapping("/create_user")

    public User createUser(@RequestBody User user) {
        userRepo.save(user);
        return user;
    }


    @PostMapping("/create_product")

    public Product createProduct(@RequestBody Product product) {
        productRepo.save(product);
        return product;
    }

    @PostMapping("/create_category")

    public Category createCategory(@RequestBody Category category) {
        categoryRepo.save(category);
        return category;

    }

    @PostMapping("/create_discount")

    public Discount createDiscount(@RequestBody Discount discount) {
        discountRepo.save(discount);
        return discount;

    }


}
