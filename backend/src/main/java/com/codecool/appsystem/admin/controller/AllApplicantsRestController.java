package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.service.ApplicantListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AllApplicantsRestController {

    @Autowired
    private ApplicantListingService applicantListingService;


    @RequestMapping(value = "/applicants", method = RequestMethod.GET)
    public @ResponseBody List<ApplicantInfoDTO> getAllApplicants(){

        //not sure how the location info will travel, so for now
        // i use this dummy data
        String locationId = "BUD";
        System.out.println(applicantListingService.addApplicationData(locationId));

        return applicantListingService.addApplicationData(locationId);
    }
}
