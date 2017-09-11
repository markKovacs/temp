package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;

import java.util.Date;
import java.util.List;

public class ApplicantDetailsDTOBuilder {

    // from user info
    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private Integer id;

    // from application info
    private Long timesApplied;
    private String location;
    private Date processStartedAt;

    private List<TestResultDTO> testResults;

    // from screening info
    private Date screeningGroupTime;
    private Date screeningPersonalTime;
    private Boolean scheduleSignedBack;
    private List<ApplicantsScreeningStepDTO> screeningSteps;
    private Boolean finalResult;

    private String phoneNumber;
    private String email;

    private String comment;


    public ApplicantDetailsDTOBuilder fromUser(User user){
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.dateOfBirth = user.getBirthDate();
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        return this;
    }

    public void setScreeningSteps(List<ApplicantsScreeningStepDTO> screeningSteps){
        this.screeningSteps = screeningSteps;
    }

    public ApplicantDetailsDTOBuilder testResults(List<TestResultDTO> testResults){
        this.testResults = testResults;
        return this;
    }

    public ApplicantDetailsDTOBuilder fromAppScrInfo(ApplicationScreeningInfo applicationScreeningInfo){
        if (applicationScreeningInfo != null) {
            this.screeningGroupTime = applicationScreeningInfo.getScreeningGroupTime();
            this.screeningPersonalTime = applicationScreeningInfo.getScreeningPersonalTime();
            this.scheduleSignedBack = applicationScreeningInfo.getScheduleSignedBack();
        }
        return this;
    }

    public ApplicantDetailsDTOBuilder fromApplication(Application application){
        this.processStartedAt = application.getProcessStartedAt();
        this.comment = application.getComment();
        return this;
    }

    public ApplicantDetailsDTOBuilder location(String location){
        this.location = location;
        return this;
    }

    public ApplicantDetailsDTOBuilder finalResult(Boolean finalResult){
        this.finalResult = finalResult;
        return this;
    }

    public ApplicantDetailsDTOBuilder timesApplied(Long timesApplied){
        this.timesApplied = timesApplied;
        return this;
    }

    public ApplicantDetailsDTO build(){

        ApplicantDetailsDTO dto = new ApplicantDetailsDTO();

        dto.setGivenName(givenName);
        dto.setFamilyName(familyName);
        dto.setDateOfBirth(dateOfBirth);
        dto.setId(id);

        dto.setTimesApplied(timesApplied);
        dto.setLocation(location);
        dto.setProcessStartedAt(processStartedAt);

        dto.setTestResults(testResults);

        dto.setScreeningGroupTime(screeningGroupTime);
        dto.setScreeningPersonalTime(screeningPersonalTime);
        dto.setScheduleSignedBack(scheduleSignedBack);

        dto.setScreeningSteps(screeningSteps);

        dto.setPhoneNumber(phoneNumber);
        dto.setEmail(email);

        dto.setComment(comment);

        return dto;

    }

}
