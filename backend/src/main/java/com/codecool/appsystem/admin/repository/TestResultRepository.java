package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestResultRepository extends JpaRepository<TestResult, String> {

    //findFirstByTestIdAndApplicationIdOrderByFinishedDesc
    TestResult findFirstByTestIdAndApplicationIdOrderByFinishedDesc(String testId, String appId);
}
