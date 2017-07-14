package com.codecool.appsystem.admin.controller;


import com.codecool.appsystem.admin.model.Question;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.repository.TestAnswerRepository;
import com.codecool.appsystem.admin.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/prerequisites", method = RequestMethod.POST)
    public RestResponseDTO applicantsByLocation(@RequestBody Question question) throws JsonProcessingException {

        questionService.saveCorrectAnswers(question);
        questionService.savePrereqTest(question);

        return RestResponseDTO.buildSuccess();

    }

}
