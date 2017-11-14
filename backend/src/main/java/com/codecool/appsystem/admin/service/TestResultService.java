package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public void deleteTestResultById(String id) {
        testResultRepository.delete(id);
    }
}
