package com.devopsson.devopsson.repository;

import com.devopsson.devopsson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
