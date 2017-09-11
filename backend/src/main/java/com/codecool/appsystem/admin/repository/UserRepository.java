package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findByLocationId(String id);

}
