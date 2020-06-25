package com.example.market;

import com.example.market.entity.Category;
import com.example.market.entity.Discount;
import com.example.market.entity.Product;
import com.example.market.entity.User;
import com.example.market.repository.CategoryRepo;
import com.example.market.repository.DiscountRepo;
import com.example.market.repository.ProductRepo;
import com.example.market.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MarketApplication {






	public static void main(String[] args) {



		SpringApplication.run(MarketApplication.class, args);
	}

}
