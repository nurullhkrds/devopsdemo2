package com.devopsson.devopsson.api;


import com.devopsson.devopsson.bussiness.abstractt.IUserService;
import com.devopsson.devopsson.entities.User;
import com.devopsson.devopsson.utilities.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserRequest user){
        return userService.addUser(user);
    }
}
