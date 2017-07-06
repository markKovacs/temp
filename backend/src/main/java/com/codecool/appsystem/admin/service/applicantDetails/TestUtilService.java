package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestUtilService {

    @Autowired
    private TestResultRepository testResRepository;

    @Autowired
    private TestRepository testRepo;




    public List<TestResultDTO> getTestInfo(Application application) {

        List<TestResult> tests = testResRepository.findByApplicationId(application.getId());

        List<TestResultDTO> result = new ArrayList<>();

        for (TestResult res: tests) {
            String name = testRepo.findOne(res.getTestId()).getName();

            TestResultDTO TDto = new TestResultDTO();
            TDto.setName(name);
            TDto.setComment(res.getComment());
            TDto.setMotivation(res.getComment());
            TDto.setPassed(res.isPassed());
            TDto.setPoints(res.getPoints());

            result.add(TDto);
        }

        return result;
    }
}
