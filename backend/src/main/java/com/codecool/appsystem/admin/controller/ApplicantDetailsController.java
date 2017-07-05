package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.service.applicantDetails.ApplicantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/applicant")
public class ApplicantDetailsController {

    @Autowired
    private ApplicantDetailsService detailsService;


    @RequestMapping(value = "/{id}")
    public @ResponseBody
    ApplicantDetailsDTO getSingleApplicantInfo(@PathVariable("id") Integer id)
    {
        return detailsService.provideInfo(id);
    }
}
