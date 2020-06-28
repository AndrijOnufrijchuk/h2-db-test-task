package com.example.market.service;


import com.example.market.entity.User;
import com.example.market.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public String addMoney(int userId, String userName, int value) {
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


}
