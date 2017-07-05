package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.UserApplicantDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantDetailsService
{

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ApplicationRepository applicationRepo;
    @Autowired
    private TestUtilService testUtil;
    @Autowired
    private UserApplicantUtilService userAppUtil;
    @Autowired
    private ApplicationScreeningInfoRepository appScrInfoRepo;


    public ApplicantDetailsDTO provideInfo(Integer id) {
        User user = userRepo.findByAdminId(id);
        Application application = applicationRepo.findByApplicantId(user.getId());

        UserApplicantDTO userApplicationDto = userAppUtil.getApplicantInfo(user,application);

        List<TestResultDTO> tests = testUtil.getTestInfo(application);

        ApplicationScreeningInfo appScrInf = appScrInfoRepo.findByApplicationId(application.getId());

        return provideDTO(userApplicationDto,tests,appScrInf);
    }


    public ApplicantDetailsDTO provideDTO(UserApplicantDTO userApplicationDto, List<TestResultDTO> tests, ApplicationScreeningInfo appScrInf)
    {
        ApplicantDetailsDTO result = new ApplicantDetailsDTO();

        result.setFamilyName(userApplicationDto.getFamilyName());
        result.setGivenName(userApplicationDto.getGivenName());
        result.setAdminId(userApplicationDto.getAdminId());
        result.setApplyingTo(userApplicationDto.getApplyingTo());
        result.setCourseId(userApplicationDto.getCourseId());
        result.setProcessStartedAt(userApplicationDto.getProcessStarted());
        result.setTimesApplied(userApplicationDto.getTimesApplied());
        result.setDateOfBirth(userApplicationDto.getDateOfBirth());
        result.setTestResults(tests);
        result.setScreeningDay(appScrInf.getScreeningDay());
        result.setScreeningGroupTime(appScrInf.getScreeningGroupTime());
        result.setScreeningPersonalTime(appScrInf.getScreeningPersonalTime());
        result.setScheduleSignedBack(appScrInf.getScheduleSignedBack());

        return result;
    }


}
