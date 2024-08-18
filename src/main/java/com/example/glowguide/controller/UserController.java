package com.example.glowguide.controller;

import com.example.glowguide.model.User;
import com.example.glowguide.service.RoutineService;
import com.example.glowguide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {



    @Autowired
    private UserService userService;


    // Endpoint to create a user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user.getName());
        return ResponseEntity.ok("User created with ID: " + newUser.getUserId());
    }


}
