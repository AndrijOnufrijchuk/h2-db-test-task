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


    @RequestMapping(value="/pay/{productIds}/{userId}", method=RequestMethod.GET)
    @ResponseBody
    public String pay(@PathVariable List<Integer> productIds,@PathVariable("userId") int userId) {
   float overallCost = 0;
  if(userRepo.findById((long)userId).isPresent()){

  for(Integer element : productIds){

      if( productRepo.findById(Long.valueOf((element))).get().getQuantity()==0){
          productRepo.delete(productRepo.findById(Long.valueOf((element))).get());
          productIds.remove(element);
      }


      if(productRepo.findById(Long.valueOf((element))).isPresent()){
float  price =  ( productRepo.findById(Long.valueOf((element))).get().getPrice() - (productRepo.findById(Long.valueOf((element))).get().getPrice() *
         (productRepo.findById((long)element).get().getPercentOfDiscount()/100.0f)));
          overallCost+= price;
      }

      else {
          return HttpStatus.NOT_FOUND.getReasonPhrase();
      }
  }
      if(userRepo.findById((long)userId).get().getBalance()>=overallCost) {
          User user = userRepo.findById((long) userId).get();
          user.setBalance(userRepo.findById((long) userId).get().getBalance() - overallCost);
          userRepo.save(user);
      }
    else{
          return HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase();
      }
      for(Integer element : productIds){





          if (productRepo.findById(Long.valueOf((element))).get().getQuantity() == 1) {
              productRepo.delete(productRepo.findById(Long.valueOf((element))).get());

          } else {
              productRepo.findById(Long.valueOf((element))).get().setQuantity(productRepo.findById(Long.valueOf((element))).get().getQuantity() - 1);
              productRepo.save(productRepo.findById(Long.valueOf((element))).get());
          }
      }


      }







   else{
       return HttpStatus.NOT_FOUND.getReasonPhrase();
  }

        return HttpStatus.OK.getReasonPhrase();
    }

    @RequestMapping(value ="/list")

    @ResponseStatus(HttpStatus.OK)
    private  LinkedList<Product> getAllProducts() {
        LinkedList<Product> products = new LinkedList<>();
        productRepo.findAll().forEach(products::add);
      return products;
    }

    @RequestMapping(value ="/default")
    @ResponseStatus(HttpStatus.OK)
            private String createDefaultValues() {
        Category category = new Category("Meat");
        Product product = new Product("Pork", 150, 30, category, 15 );
        Discount discount = new Discount(product);
        User user = new User("Andrusha", 500);


        categoryRepo.save(category);
        discountRepo.save(discount);
        userRepo.save(user);
        productRepo.save(product);
        return HttpStatus.OK.getReasonPhrase();
    }

    @PostMapping("/create_user")
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@RequestBody User user) {
        userRepo.save(user);
        return user;
    }


    @PostMapping("/create_product")
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(@RequestBody Product product) {
        productRepo.save(product);
        discountRepo.save(new Discount(product));
        return product;
    }

    @PostMapping("/create_category")
    @ResponseStatus(HttpStatus.OK)
    public Category createCategory(@RequestBody Category category) {
        categoryRepo.save(category);
        return category;

    }

    @PostMapping("/create_discount")
    @ResponseStatus(HttpStatus.OK)
    public Discount createDiscount(@RequestBody Discount discount) {
        discountRepo.save(discount);
        return discount;
    }


}
