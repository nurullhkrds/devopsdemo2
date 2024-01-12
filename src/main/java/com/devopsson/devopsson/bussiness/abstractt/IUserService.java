package com.devopsson.devopsson.bussiness.abstractt;



import com.devopsson.devopsson.entities.User;
import com.devopsson.devopsson.utilities.UserRequest;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();


    String addUser(UserRequest user);
}
