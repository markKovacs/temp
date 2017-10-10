package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findByLocationId(String locationId);

    List<Application> findByLocationIdAndActiveIsTrueAndFinalResultIsNull(String locationId);

    List<Application> findByFinalResultIsNullAndFinalResultSentIsNull();

    List<Application> findByFinalResultIsTrueAndCourseIdIsNull();

}
