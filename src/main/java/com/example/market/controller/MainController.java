package com.example.market.controller;



import com.example.market.entity.Product;
import com.example.market.entity.User;
import com.example.market.repository.CategoryRepo;
import com.example.market.repository.DiscountRepo;
import com.example.market.repository.ProductRepo;
import com.example.market.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
    @ResponseBody
    public String homePage(){

        return "hi Andrusha";
}



@PostMapping("/create_user")
    @ResponseBody
    public String createUser(@RequestBody User user){
            userRepo.save(user);
        return  new String("username " + user.getName() + " user balance " + user.getBalance());
    }
 @PostMapping("/create_product")
    @ResponseBody
    public String createProduct(@RequestBody Product product){

    // System.out.println("HALO" + product.getCategory().id + product.getPrice() + product.getName() + product.getDiscount().getId());
     productRepo.save(product);
     return new String("product " + product.getName() + " product price " + product.getPrice());
 }



}
