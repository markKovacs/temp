package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import com.codecool.appsystem.admin.service.ScreeningEvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningStepsController {

    @Autowired
    private ScreeningEditService screeningEditService;

    @Autowired
    private ScreeningEvalService screeningEvalService;

    @RequestMapping(value = "/api/screeningsteps", method = RequestMethod.GET)
    public List<ScreeningStep> findByLocationId(@RequestParam String location){
        return screeningEditService.findByLocationId(location);
    }

    @RequestMapping(value = "/api/evalscreening/{applicantId}", method = RequestMethod.GET)
    public ScreeningStepEvaluationDTO findForApplicant(@PathVariable("applicantId") Integer adminId, @RequestParam("step") String stepId){
        return screeningEditService.findForApplicant(adminId, stepId);
    }

    @RequestMapping(value = "/api/evalscreening", method = RequestMethod.POST)
    public RestResponseDTO saveEvaluation(@RequestBody ApplicantsScreeningStep data){
        screeningEditService.saveEvaluation(data);
        return RestResponseDTO.buildSuccess();
    }

    @RequestMapping(value = "/api/setfinalresult", method = RequestMethod.POST)
    public RestResponseDTO applicantsByLocation(@RequestBody ScreeningGrade grade) {

        screeningEvalService.gradeScreening(grade);
        return RestResponseDTO.buildSuccess();

    }

}
