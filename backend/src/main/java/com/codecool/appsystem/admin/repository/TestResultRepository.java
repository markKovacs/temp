package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TestResultRepository extends JpaRepository<TestResult, String> {

    //findFirstByTestIdAndApplicationIdOrderByFinishedDesc
    TestResult findFirstByTestIdAndApplicationIdOrderByFinishedDesc(String testId, String appId);

    List<TestResult> findByApplicationId(String id);

    TestResult findByTestIdAndApplicationId(String testId, String appId);

    List<TestResult> findByApplicationIdAndPassed(String id, boolean passed);
}
