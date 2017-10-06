package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.service.ApplicantAdminService;
import com.codecool.appsystem.admin.service.ApplicantDetailsService;
import com.codecool.appsystem.admin.service.ApplicantListingService;
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

    @Autowired
    private ApplicantAdminService applicantAdminService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ApplicantInfoDTO> getAllApplicants(@RequestParam("location") String locationId, @RequestParam(value = "all", required = false) Boolean all) {
        return applicantListingService.getApplicationData(locationId, all);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApplicantDetailsDTO getSingleApplicantInfo(@PathVariable("id") Integer id) {
        return detailsService.provideInfo(id);
    }

    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public List<ApplicantInfoDTO> getFinishedApplicants() {
        return applicantListingService.getFinished();
    }

    @RequestMapping(value = "/{id}/savedate", method = RequestMethod.POST)
    public boolean saveDate(
            @PathVariable("id") Integer id, @RequestBody Map<String, Long> data) {
        detailsService.saveDates(id, data);
        return true;
    }

    @RequestMapping(value = "/{id}/terminate", method = RequestMethod.GET)
    public boolean terminateApplication(@PathVariable("id") Integer id) {
        detailsService.terminate(id);
        return true;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public boolean deleteApplicantFromSystem(@PathVariable("id") Integer id){
        applicantAdminService.delete(id);
        return true;
    }
}
