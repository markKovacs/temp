package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findByLocationId(String locationId);

    List<Application> findByLocationIdAndActiveIsTrue(String locationId);

    List<Application> findByUserAndActiveIsNot(User user, Boolean active);

}
