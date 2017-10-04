package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.TestDTO;
import com.codecool.appsystem.admin.repository.LocationRepository;
import com.codecool.appsystem.admin.repository.TestAnswerRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Autowired
    private LocationRepository locationRepository;

    public void saveTest(Question question) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        Test test = new Test();
        test.setId(question.getId());

        test.setFormAsJson(mapper.writeValueAsString(question));
        test.setMaxPoints(question.getMaxPoints());
        test.setThreshold(question.getThreshold());
        test.setEnabled(question.getEnabled());
        test.setEstimatedTime(question.getEstimatedTime());
        test.setName(question.getName());
        test.setOrderInBundle(question.getOrderInBundle());

        test.setLocation(locationRepository.findOne(question.getLocationId()));

        test.setDescription(question.getDescription());
        test.setMotivationVideo(question.getMotivationVideo());

        testRepository.save(test);
    }

    public void saveCorrectAnswers(Question question) {

        for (QuestionContent questionContent : question.getQuestions()) {

            for (QuestionOption questionOption : questionContent.getOptions()) {

                List<TestAnswer> existingAnswers = testAnswerRepository.findByQuestionIdOrderByCorrectAnswerAsc(questionContent.getId());
                testAnswerRepository.delete(existingAnswers);

                if (questionOption.getIsCorrect()) {
                    TestAnswer testAnswer = new TestAnswer();
                    testAnswer.setQuestionId(questionContent.getId());
                    testAnswer.setCorrectAnswer(questionOption.getId());

                    testAnswerRepository.save(testAnswer);
                }
                // null out the isCorrect flag.
                questionOption.setIsCorrect(null);
            }
        }
    }

    public List<Question> createQuestionsFromLocationId(List<Test> testsByLocation) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<Question> questions = new ArrayList<>();


        for (Test test : testsByLocation) {
            if(!StringUtils.isEmpty(test.getFormAsJson())) {
                questions.add(mapper.readValue(test.getFormAsJson(), Question.class));
            }
        }

        return addCorrectAnswerToQuestions(questions);
    }

    private List<Question> addCorrectAnswerToQuestions(List<Question> questions) {

        for (Question question : questions) {
            for (QuestionContent qComponent : question.getQuestions()) {
                List<String> testAnswerOptionId = testAnswerRepository.findByQuestionIdOrderByCorrectAnswerAsc(qComponent.getId()).stream().map(TestAnswer::getCorrectAnswer).collect(Collectors.toList());
                for (QuestionOption qOption : qComponent.getOptions()) {
                    if (testAnswerOptionId.contains(qOption.getId())) {
                        qOption.setIsCorrect(true);
                    } else {
                        qOption.setIsCorrect(false);
                    }
                }
            }
        }
        return questions;
    }

    public TestDTO createQuestionDTO(Test test, Question question) {

        return TestDTO.builder()
                .description(test.getDescription())
                .enabled(test.getEnabled())
                .estimatedTime(test.getEstimatedTime())
                .locationId(test.getLocation().getId())
                .id(test.getId())
                .maxPoints(test.getMaxPoints())
                .motivationVideo((test.getMotivationVideo() != null) ? test.getMotivationVideo() : false)
                .name(test.getName())
                .orderInBundle(test.getOrderInBundle())
                .threshold(test.getThreshold())
                .surveyContent(question.getSurveyContent())
                .questions(question.getQuestions())
                .build();

    }
}
