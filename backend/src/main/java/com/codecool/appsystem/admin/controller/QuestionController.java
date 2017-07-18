package com.codecool.appsystem.admin.controller;


import com.codecool.appsystem.admin.model.Question;
import com.codecool.appsystem.admin.model.Test;
import com.codecool.appsystem.admin.model.dto.QuestionDTO;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
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
    public RestResponseDTO applicantsByLocation(@RequestBody Question question) throws JsonProcessingException {

        questionService.saveCorrectAnswers(question);
        questionService.savePrereqTest(question);

        return RestResponseDTO.buildSuccess();

    }

    @RequestMapping(value="/{location}", method = RequestMethod.GET)
    public List<QuestionDTO> getQuestionByLocation(@PathVariable("location") String locationId) throws IOException {

        List<QuestionDTO> result = new ArrayList<>();
        List<Test> testsByLocation = testRepository.findByLocationIdOrderByOrderInBundleAsc(locationId);
        List<Question> questions = questionService.createQuestionsFromLocationId(testsByLocation);

        for (Test test : testsByLocation) {
            for (Question question : questions) {
                result.add(questionService.createQuestionDTO(test, question));
            }
        }

        return result;
    }

}
