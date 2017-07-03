package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, String> {

    Optional<Authentication> findById(String userEmail);


}
