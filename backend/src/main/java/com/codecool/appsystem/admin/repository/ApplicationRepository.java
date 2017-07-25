package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findByApplicantIdAndActive(String email, Boolean active);

    List<Application> findByLocationId(String locationId);

    List<Application> findByLocationIdAndActive(String locationId, Boolean active);

    Long countByApplicantId(String id);

}
