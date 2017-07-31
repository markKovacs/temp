package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {

    List<EmailTemplate> findByLocationId(String name);

}
