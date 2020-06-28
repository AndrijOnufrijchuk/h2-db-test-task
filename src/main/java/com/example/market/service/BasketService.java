package com.example.market.service;

import com.example.market.entity.Basket;
import com.example.market.entity.Product;
import com.example.market.repository.BasketRepo;
import com.example.market.repository.ProductRepo;
import com.example.market.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    private BasketRepo basketRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;

    public List<Basket> findAllByUserId(long userId) {
        List<Basket> basketList = new ArrayList<>();

        List<Basket> baskets = (List<Basket>) basketRepo.findAll();
        for (Basket basket : baskets) {
            if (basket.getUser().getId() == userId) {
                basketList.add(basket);

            }

        }

        return basketList;
    }

    public String buy(List<Integer> productIds, int userId) {

        float overallCost = 0;
        //check if user exists
        if (userRepo.findById((long) userId).isPresent()) {

            if (!basketRepo.findAllByUserId((long) userId).isEmpty()) {
                //find all elements from basket with specified user

                List<Basket> baskets = basketRepo.findAllByUserId(userId);

                for (Basket basket : baskets) {


                    float price = (basket.getProduct().getPrice() - (basket.getProduct().getPrice() * basket.getProduct().getPercentOfDiscount() / 100.0f)) * basket.getUserQuantity();


                    overallCost += price;
                }


//check if user has enough balance to pay
                if (userRepo.findById((long) userId).get().getBalance() >= overallCost) {

                    userRepo.findById((long) userId).get().setBalance(userRepo.findById((long) userId).get().getBalance() - overallCost);
                } else {
                    return HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase();
                }


                for (Integer productId : productIds) {

                    if (productRepo.findById((long) productId).isPresent()) {
                        for (Basket basket : baskets) {
                            //check if there is enough product quantity
                            if (basket.getUserQuantity() > productRepo.findById((long) productId).get().getQuantity()) {
                                return HttpStatus.BAD_REQUEST.getReasonPhrase();
                            }


                            if (basket.getProduct().getId() == productRepo.findById((long) productId).get().getId()) {

                                if (basket.getUserQuantity() <= productRepo.findById((long) productId).get().getQuantity()) {

//subtraction quantity that was bought

                                    Product product = productRepo.findById(((long) productId)).get();

                                    product.setQuantity(productRepo.findById((long) productId).get().getQuantity() - basket.getUserQuantity());
                                    basketRepo.delete(basket);


                                    productRepo.save(product);


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


}
