package com.example.market.controller;


import com.example.market.entity.*;
import com.example.market.repository.*;
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


    @RequestMapping(value = "/pay/{productIds}/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String pay(@PathVariable List<Integer> productIds, @PathVariable("userId") int userId) {
        float overallCost = 0;
        //check if user exists
        if (userRepo.findById((long) userId).isPresent()) {
            System.out.println("user exist");
            if (!basketRepo.findAllByUserId((long) userId).isEmpty()) {
                //find all elements from basket with specified user
                System.out.println("basket for that user exist");
                List<Basket> baskets = basketRepo.findAllByUserId(userId);
//calculate overall cost
                for (Basket basket : baskets) {
                    System.out.println("Basket iterating");


                    float price = (basket.getProduct().getPrice() - (basket.getProduct().getPrice() * basket.getProduct().getPercentOfDiscount() / 100.0f)) * basket.getUserQuantity();
                    System.out.println("price " + price);
                    overallCost += price;
                }

//check if user has enough balance to pay
                if (userRepo.findById((long) userId).get().getBalance() >= overallCost) {

                    userRepo.findById((long) userId).get().setBalance(userRepo.findById((long) userId).get().getBalance() - overallCost);
                } else {
                    return HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase();
                }


                for (Integer productId : productIds) {
                    Long pId = new Long(productId);
                    if (productRepo.findById(pId).isPresent()) {
                        for (Basket basket : baskets) {
                            //check if there is enough product quantity
                            if (basket.getUserQuantity() > productRepo.findById(pId).get().getQuantity()) {
                                return HttpStatus.BAD_REQUEST.getReasonPhrase();
                            }


                            if (basket.getProduct().getId() == productRepo.findById(pId).get().getId()) {

                                if (basket.getUserQuantity() <= productRepo.findById(pId).get().getQuantity()) {

//subtraction quntity that was buyed or delete if quantity = 0

                                        Product product = productRepo.findById(Long.valueOf((pId))).get();

                                        product.setQuantity(productRepo.findById(Long.valueOf((pId))).get().getQuantity() - basket.getUserQuantity());
                                        basketRepo.delete(basket);

                                        if (productRepo.findById(Long.valueOf((pId))).get().getQuantity() == 0) {
                                            productRepo.delete(product);
                                        } else {
                                            productRepo.save(product);
                                        }




                                }
                            }
                        }

                    }


                }


            } else {
                return HttpStatus.NOT_FOUND.getReasonPhrase();
            }

        } else {
            return HttpStatus.NOT_FOUND.getReasonPhrase();
        }


        return HttpStatus.OK.getReasonPhrase();
    }

    @RequestMapping(value = "/list")

    @ResponseStatus(HttpStatus.OK)
    private LinkedList<Product> getAllProducts() {
        LinkedList<Product> products = new LinkedList<>();
        productRepo.findAll().forEach(products::add);
        return products;
    }

    @RequestMapping(value = "/default")
    @ResponseStatus(HttpStatus.OK)
    private String createDefaultValues() {
        Category category = new Category("Meat");
        Product product = new Product("Pork", 150, 30, category, 15);
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
    public Object createProduct(@RequestBody Product product) {
        if (product.getPercentOfDiscount() < 0) {
            return HttpStatus.NOT_ACCEPTABLE.getReasonPhrase();
        }
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

    @PostMapping("/create_basket")
    public Basket addProductToBasket(@RequestBody Basket basket) {
        basketRepo.save(basket);
        return basket;
    }


}
