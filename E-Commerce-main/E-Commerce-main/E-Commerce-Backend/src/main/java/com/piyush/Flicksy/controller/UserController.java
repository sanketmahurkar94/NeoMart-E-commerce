package com.piyush.Flicksy.controller;

import com.piyush.Flicksy.model.User;
import com.piyush.Flicksy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public User registerUser(@RequestBody User user){

        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){

        return userService.loginUser(user.getEmail(), user.getPassword());
    }
    @GetMapping
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }

}
