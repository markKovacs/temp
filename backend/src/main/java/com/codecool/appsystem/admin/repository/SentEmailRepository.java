package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentEmailRepository extends JpaRepository<SentEmail, String> {
}
