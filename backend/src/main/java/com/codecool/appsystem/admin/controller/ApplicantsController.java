package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.service.ApplicantListingService;
import com.codecool.appsystem.admin.service.applicantDetails.ApplicantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/applicants")
public class ApplicantsController {

    @Autowired
    private ApplicantDetailsService detailsService;

    @Autowired
    private ApplicantListingService applicantListingService;


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<ApplicantInfoDTO> getAllApplicants(){

        //not sure how the location info will travel, so for now
        // i use this dummy data
        String locationId = "BUD";

        return applicantListingService.addApplicationData(locationId);
    }


    @RequestMapping(value = "/{id}")
    public @ResponseBody
    ApplicantDetailsDTO getSingleApplicantInfo(@PathVariable("id") Integer id) {
        
        return detailsService.provideInfo(id);
    }
}
