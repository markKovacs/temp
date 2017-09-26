package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TestResultRepository extends JpaRepository<TestResult, String> {

    List<TestResult> findByApplicationId(String id);

    @Query(nativeQuery = true, value = "SELECT id FROM v_timed_out_tests")
    List<String> getTimedOutTests();

}
