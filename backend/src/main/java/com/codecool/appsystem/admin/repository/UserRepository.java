package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserHash(String userHash);
}
