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
    public List<ApplicantInfoDTO> getAllApplicants(@RequestParam("location") String locationId){
        return applicantListingService.getApplicationData(locationId);
    }

    @RequestMapping(value = "/{id}")
    public ApplicantDetailsDTO getSingleApplicantInfo(@PathVariable("id") Integer id) {
        return detailsService.provideInfo(id);
    }
}
