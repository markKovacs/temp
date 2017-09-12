package com.codecool;


import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;
import java.util.Date;

public abstract class MockData {

    protected Location location;
    protected ApplicationScreeningInfo applicationScreeningInfo;
    protected Application application;
    protected User user;
    protected TestResult testResult;
    protected TestResult testResult1;
    protected TestResult testResult2;
    protected TestResult testResult3;
    protected Test test;
    protected Question question;
    protected QuestionContent qcon1;
    protected QuestionContent qcon2;
    protected QuestionOption option;
    protected QuestionOption option2;
    protected Test test2;
    protected MotivationDTO motivDto;

    @Before
    public void setUpMockData(){

        user = new User();
        user.setLocationId("BUD");
        user.setId(1);
        user.setEmail("asd@gmail.com");
        user.setCanApply(true);
        user.setBirthDate(1991);
        user.setFamilyName("John");
        user.setGivenName("Doe");
        user.setIsBlacklisted(false);

        application = new Application();
        application.setId("1");
        application.setActive(true);
        application.setApplicantId(1);

        location = new Location();
        location.setName("Budapest");
        location.setId("BUD");
        location.setCourseType("General");

        applicationScreeningInfo = new ApplicationScreeningInfo();
        applicationScreeningInfo.setId("1");
        applicationScreeningInfo.setApplicationId("1");
        applicationScreeningInfo.setScheduleSignedBack(false);
        applicationScreeningInfo.setScreeningGroupTime(new Date());
        applicationScreeningInfo.setScreeningPersonalTime(new Date());

        test = new Test();
        test.setId("bp-2017-english");
        test.setLocationId("BUD");
        test.setMaxPoints(10);
        test.setThreshold(6);
        test.setName("English");
        test.setEnabled(true);
        test.setMotivationVideo(false);


        testResult = new TestResult();
        testResult.setId("0");
        testResult.setPassed(true);
        testResult.setApplicationId("1");
        testResult.setTestId("bp-2017-prereq");
        testResult.setSavedAnswers("bla blabla");
        testResult.setPercent(100D);

        testResult1 = new TestResult();
        testResult1.setId("1");
        testResult1.setPassed(true);
        testResult1.setApplicationId("1");
        testResult1.setTestId("bp-2017-logic");
        testResult1.setSavedAnswers("bla blabla");

        testResult2 = new TestResult();
        testResult2.setId("2");
        testResult2.setPassed(true);
        testResult2.setApplicationId("1");
        testResult2.setTestId("bp-2017-english");
        testResult2.setSavedAnswers("bla blabla");

        testResult3 = new TestResult();
        testResult3.setId("3");
        testResult3.setApplicationId("1");
        testResult3.setTestId("test_id");
        testResult3.setSavedAnswers("bla blabla");

        option = new QuestionOption();
        option.setId("opt1");
        option.setIsCorrect(false);
        option.setOptionContent("Content");

        option2 = new QuestionOption();
        option2.setId("opt2");
        option2.setIsCorrect(true);
        option2.setOptionContent("Content2");

        qcon1 = new QuestionContent();
        qcon1.setId("one");
        qcon1.setGraded(false);
        qcon1.setOptions(Arrays.asList(option,option2));
        qcon1.setType("Type");
        qcon1.setQuestionContent("Q content");

        qcon2 = new QuestionContent();
        qcon2.setId("two");
        qcon2.setGraded(false);
        qcon2.setOptions(Arrays.asList(option,option2));
        qcon2.setType("Type");
        qcon2.setQuestionContent("Q content");

        question = new Question();
        question.setId("test_id");
        question.setEnabled(true);
        question.setMotivationVideo(false);
        question.setName("Test Mock");
        question.setDescription("Descr");
        question.setLocationId("BUD");
        question.setEstimatedTime(20);
        question.setMaxPoints(10);
        question.setOrderInBundle(2);
        question.setThreshold(8);
        question.setQuestions(Arrays.asList(qcon1,qcon2));
        question.setSurveyContent("Content");

        test2 = new Test();
        test2.setId("bp-2017-prereq");
        test2.setEnabled(true);
        test2.setMotivationVideo(true);
        test2.setName("Test Mock");
        test2.setDescription("Descr");
        test2.setLocationId("BUD");
        test2.setEstimatedTime(20);
        test2.setMaxPoints(10);
        test2.setOrderInBundle(2);
        test2.setThreshold(8);

        motivDto = new MotivationDTO();
        motivDto.setName(user.getFullName());
        motivDto.setId(user.getId());
        motivDto.setIsVideo(false);
    }

    @After
    public void tearDownMockData(){
        location = null;
        applicationScreeningInfo = null;
        application = null;
        user = null;
        testResult = null;
        testResult1 = null;
        testResult2 = null;
        testResult3 = null;
        test = null;
        question = null;
        qcon1 = null;
        qcon2 = null;
        option = null;
        option2 = null;
        test2 = null;
    }
}
