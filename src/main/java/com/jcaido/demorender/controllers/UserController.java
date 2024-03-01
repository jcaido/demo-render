package com.jcaido.demorender.controllers;

import com.jcaido.demorender.models.User;
import com.jcaido.demorender.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<User> gettAllUsers() {
        return userRepository.findAll();
    }
}
