package com.codecool.appsystem.admin.controller;


import com.codecool.appsystem.admin.model.Question;
import com.codecool.appsystem.admin.model.Test;
import com.codecool.appsystem.admin.model.dto.TestDTO;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestRepository testRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveQuestion(@RequestBody Question question) throws JsonProcessingException {

        log.info("new Survey Arrived: " + question);
        questionService.saveCorrectAnswers(question);
        questionService.saveTest(question);

        return true;
    }

    @RequestMapping(value="/{location}", method = RequestMethod.GET)
    public List<TestDTO> getQuestionByLocation(@PathVariable("location") String locationId) throws IOException {

        List<TestDTO> result = new ArrayList<>();
        List<Test> testsByLocation = testRepository.findByLocationIdOrderByOrderInBundleAsc(locationId);
        List<Question> questions = questionService.createQuestionsFromLocationId(testsByLocation);

        for (int i = 0; i < testsByLocation.size(); i++) {
            result.add(questionService.createQuestionDTO(testsByLocation.get(i), questions.get(i)));
        }

        return result;
    }

}
