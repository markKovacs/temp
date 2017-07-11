package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.applicantDetails.UserApplicantDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserApplicantUtilService {

    @Autowired
    private LocationRepository location;

    @Autowired
    private ApplicationRepository applicationRepo;


    public UserApplicantDTO getApplicantInfo(User user, Application application) {

        String school = location.findOne(user.getLocationId()).getName();
        Long timesApplied = applicationRepo.countByApplicantId(user.getId());

        UserApplicantDTO details = new UserApplicantDTO();
        // TODO = REFACTOR
        details.setAdminId(user.getAdminId());
        details.setApplyingTo(school);
        details.setDateOfBirth(user.getBirthDate() == null ? null : user.getBirthDate());
        details.setFamilyName(user.getFamilyName() == null ? " " : user.getFamilyName());
        details.setGivenName(user.getFamilyName() == null ? " " : user.getFamilyName());
        details.setTimesApplied(timesApplied);
        details.setProcessStarted(application.getProcessStartedAt() == null ? new Date() : application.getProcessStartedAt());
        details.setCourseId(application.getCourseId() == null ? 0 : application.getCourseId());

        return details;
    }
}
