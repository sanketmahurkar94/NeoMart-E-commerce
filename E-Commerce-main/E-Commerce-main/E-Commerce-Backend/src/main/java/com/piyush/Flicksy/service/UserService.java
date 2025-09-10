package com.piyush.Flicksy.service;

import com.piyush.Flicksy.model.User;
import com.piyush.Flicksy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        try {
            User newUser = userRepository.save(user);
            System.out.println("User Added to the Database");
            return newUser;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public User loginUser(String email, String password) {
        //check if user is there or not
        User user = userRepository.getByEmail(email);

        if (user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;//invalid credentials of user
    }


    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
