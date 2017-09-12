package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findByApplicantIdAndActiveIsTrue(Integer id);

    List<Application> findByLocationId(String locationId);

    List<Application> findByLocationIdAndActiveIsTrue(String locationId);

    Long countByApplicantId(Integer id);

}
