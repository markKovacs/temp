package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    Application findByApplicantIdAndActiveIsTrue(Integer id);

    List<Application> findByLocationId(String locationId);

    List<Application> findByLocationIdAndActiveIsTrue(String locationId);

    Long countByApplicantId(Integer id);

    @Query(nativeQuery = true, value = "select * from online_app_system.applications where applicant_id = :applicantId order by process_started_at desc NULLS LAST limit 1")
    Application findLast(@Param("applicantId") Integer applicantId);


}
