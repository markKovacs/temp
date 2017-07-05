package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantDetailsService
{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ApplicationRepository applicationRepo;
    @Autowired
    private ApplicationScreeningInfoRepository appScrRepo;
    @Autowired
    private TestResultRepository resultsRepo;

    public ApplicantDetailsDTO provideInfo(Integer id) {
        return null;
    }
}
