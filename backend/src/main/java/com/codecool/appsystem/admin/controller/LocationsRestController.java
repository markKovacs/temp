package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.QuestionDTO;
import com.codecool.appsystem.admin.repository.LocationRepository;
import com.codecool.appsystem.admin.repository.TestAnswerRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@RestController
@RequestMapping("/api/locations")
public class LocationsRestController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }


    @RequestMapping(value="/test/{location}", method = RequestMethod.GET)
    public List<Question> getQuestionByLocation(@PathVariable("location") String location) throws IOException {
        log.trace("LOCATION CONTROLLER");

        ObjectMapper mapper = new ObjectMapper();
        List<QuestionDTO> result = new ArrayList<>();
        List<Test> testsByLocation = testRepository.findByLocationIdOrderByOrderInBundleAsc(location);
        List<Question> questions = new ArrayList<>();
        for (Test test : testsByLocation) {
            questions.add(mapper.readValue(test.getFormAsJson(), Question.class));
            log.trace("question" + mapper.readValue(test.getFormAsJson(), Question.class));
        }

        for (Question question : questions) {
            for (QuestionContent qC : question.getQuestions()) {
                List<String> testAnswerOptionId = testAnswerRepository.findByQuestionIdOrderByCorrectAnswerAsc(qC.getId()).stream().map(TestAnswer::getCorrectAnswer).collect(Collectors.toList());
                log.trace("testAnser" + testAnswerOptionId);
                for (QuestionOption qO : qC.getOptions()) {
                    if (testAnswerOptionId.contains(qO.getId())){
                        qO.setIsCorrect(true);
                        log.trace("Option Answer is Correct: " + qO);
                    } else{
                        qO.setIsCorrect(false);
                        log.trace("Option Answer is False: " + qO);
                    }
                }
            }
        }


        return questions;
    }


}
