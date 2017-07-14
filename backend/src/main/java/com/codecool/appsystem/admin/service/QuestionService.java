package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.repository.TestAnswerRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //// FIXME: 2017.07.14. what is the right data?
        test.setEstimatedTime(5);
        test.setName("prerequisite");
        test.setOrderInBundle(0);
        //// FIXME: 2017.07.14. missing description
        //test.setFormUrl();
        //test.setLocationId();
        testRepository.save(test);
    }

    public void saveCorrectAnswers(Question question){

        for (QuestionContent questionContent : question.getQuestions()) {
            for (QuestionOption questionOption : questionContent.getOptions()) {

                if (questionOption.getIsCorrect()){
                    TestAnswer testAnswer = new TestAnswer();
                    testAnswer.setQuestionId(questionOption.getId());
                    testAnswer.setCorrectAnswer(questionOption.getOptionContent());

                    testAnswerRepository.save(testAnswer);

                    questionOption.setIsCorrect(null);
                }
            }
        }
    }
}
