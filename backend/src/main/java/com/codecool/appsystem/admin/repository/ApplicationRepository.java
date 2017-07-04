package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findByApplicantIdAndActive(String email, Boolean active);

    Application findByApplicantId(String id);

}
