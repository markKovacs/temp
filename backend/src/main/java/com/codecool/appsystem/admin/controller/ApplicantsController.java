package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.Email;
import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.model.dto.PersonalDataDTO;
import com.codecool.appsystem.admin.service.*;
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

    @Autowired
    private PersonalDataService personalDataService;

    @Autowired
    private EmailService emailService;

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

    @RequestMapping(value = "/hired", method = RequestMethod.GET)
    public List<ApplicantInfoDTO> getHiredApplicants() {
        return applicantListingService.getHired();
    }

    @RequestMapping(value = "/personaldata", method = RequestMethod.GET)
    public List<PersonalDataDTO> personalData() {
        return personalDataService.list();
    }

    @RequestMapping(value = "/contractsigned/{id}", method = RequestMethod.GET)
    public boolean contractSigned(@PathVariable("id") Integer id, @RequestParam("courseId") String courseId) {
        return personalDataService.setContractSigned(id, courseId);
    }

    @RequestMapping(value = "/rejected/{id}", method = RequestMethod.GET)
    public boolean rejected(@PathVariable("id") Integer id) {
        return personalDataService.setRejected(id);
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

    @RequestMapping(value = "/restore/{applicationId}", method = RequestMethod.GET)
    public boolean restoreApplication(@PathVariable("applicationId") String applicationId){
        detailsService.restoreApplication(applicationId);
        return true;
    }

    @RequestMapping(value = "/{id}/emails", method = RequestMethod.GET)
    public List<Email> sentEmails(@PathVariable("id") Integer id){
        return emailService.findByAddressee(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public boolean deleteApplicantFromSystem(@PathVariable("id") Integer id){
        applicantAdminService.delete(id);
        return true;
    }
}
