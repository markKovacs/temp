package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.service.ApplicantListingService;
import com.codecool.appsystem.admin.service.applicantDetails.ApplicantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/applicants")
public class ApplicantsController {

    @Autowired
    private ApplicantDetailsService detailsService;

    @Autowired
    private ApplicantListingService applicantListingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ApplicantInfoDTO> getAllApplicants(@RequestParam("location") String locationId, @RequestParam(value = "all", required = false) Boolean all) {
        return applicantListingService.getApplicationData(locationId, all);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApplicantDetailsDTO getSingleApplicantInfo(@PathVariable("id") Integer id) {
        return detailsService.provideInfo(id);
    }

    @RequestMapping(value = "/{id}/savedate", method = RequestMethod.POST)
    public ApplicantDetailsDTO getSingleApplicantInfo(
            @PathVariable("id") Integer id, @RequestBody Map<String, Long> data) {
        return detailsService.saveDates(id, data);
    }
}
