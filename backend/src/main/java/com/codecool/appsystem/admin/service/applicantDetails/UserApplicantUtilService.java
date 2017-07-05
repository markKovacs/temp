package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.applicantDetails.UserApplicantDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        details.setAdminId(user.getAdminId());
        details.setApplyingTo(school);
        details.setDateOfBirth(user.getBirthDate());
        details.setFamilyName(user.getFamilyName());
        details.setGivenName(user.getGivenName());
        details.setTimesApplied(timesApplied);
        details.setProcessStarted(application.getProcessStartedAt());
        details.setCourseId(application.getCourseId());

        return details;
    }
}
