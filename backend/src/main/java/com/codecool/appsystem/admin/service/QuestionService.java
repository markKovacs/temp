package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.QuestionDTO;
import com.codecool.appsystem.admin.repository.TestAnswerRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    public void savePrereqTest(Question question) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Test test = new Test();
        test.setId(question.getId());

        test.setFormAsJson(mapper.writeValueAsString(question));
        test.setMaxPoints(question.getQuestions().size());
        test.setThreshold(question.getQuestions().size());
        test.setEnabled(true);
        test.setEstimatedTime(5);
        test.setName("Prerequisite");
        test.setOrderInBundle(0);
        test.setFormUrl(question.getFormUrl());
        test.setLocationId(question.getLocationId());
        test.setDescription(question.getDescription());

        testRepository.save(test);
    }

    public void saveCorrectAnswers(Question question){

        for (QuestionContent questionContent : question.getQuestions()) {
            for (QuestionOption questionOption : questionContent.getOptions()) {

                if (questionOption.getIsCorrect()){
                    TestAnswer testAnswer = new TestAnswer();
                    testAnswer.setQuestionId(questionContent.getId());
                    testAnswer.setCorrectAnswer(questionOption.getId());

                    testAnswerRepository.save(testAnswer);
                }
                    questionOption.setIsCorrect(null);
            }
        }
    }

    public List<Question> createQuestionsFromLocationId(List<Test> testsByLocation) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<Question> questions = new ArrayList<>();


        for (Test test : testsByLocation) {
            questions.add(mapper.readValue(test.getFormAsJson(), Question.class));
        }

        return addCorrectAnswerToQuestions(questions);
    }

    private  List<Question> addCorrectAnswerToQuestions(List<Question> questions){

        for (Question question : questions) {
            for (QuestionContent qComponent : question.getQuestions()) {
                List<String> testAnswerOptionId = testAnswerRepository.findByQuestionIdOrderByCorrectAnswerAsc(qComponent.getId()).stream().map(TestAnswer::getCorrectAnswer).collect(Collectors.toList());
                for (QuestionOption qOption : qComponent.getOptions()) {
                    if (testAnswerOptionId.contains(qOption.getId())){
                        qOption.setIsCorrect(true);
                    } else{
                        qOption.setIsCorrect(false);
                    }
                }
            }
        }
        return questions;
    }

    public QuestionDTO createQuestionDTO(Test test, Question question){

        return QuestionDTO.builder()
                .description(test.getDescription())
                .enabled(test.getEnabled())
                .estimatedTime(test.getEstimatedTime())
                .locationId(test.getLocationId())
                .id(test.getId())
                .maxPoints(test.getMaxPoints())
                .motivationVideo((test.getMotivationVideo() != null) ? test.getMotivationVideo(): false)
                .name(test.getName())
                .orderInBundle(test.getOrderInBundle())
                .threshold(test.getThreshold())
                .surveyContent(question.getSurveyContent())
                .questions(question.getQuestions())
                .build();

    }
}
