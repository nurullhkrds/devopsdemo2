package com.devopsson.devopsson.bussiness.concretes;


import com.devopsson.devopsson.bussiness.abstractt.IUserService;
import com.devopsson.devopsson.entities.User;
import com.devopsson.devopsson.repository.IUserRepository;
import com.devopsson.devopsson.utilities.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements IUserService {

    private final IUserRepository userRepository;

    public UserManager(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String addUser(UserRequest user) {

        User userTest=new User();
        userTest.setEmail(user.getEmail());
        userTest.setName(user.getName());
        userTest.setPassword(user.getPassword());

        userRepository.save(userTest);
        return "User added";
    }
}
