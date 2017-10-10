package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.dto.ApplicantsScreeningStepDTO;
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
    public ScreeningStepEvaluationDTO findForApplicant(@PathVariable("applicantId") Integer id, @RequestParam(value = "step", required = false) String stepId){
        return screeningEditService.findForApplicant(id, stepId);
    }

    @RequestMapping(value = "/api/screening/getsteps/{applicantId}", method = RequestMethod.GET)
    public List<ScreeningStepEvaluationDTO> findForApplicant(@PathVariable("applicantId") Integer id){
        return screeningEditService.getSteps(id);
    }

    @RequestMapping(value = "/api/evalscreening", method = RequestMethod.POST)
    public boolean saveEvaluation(@RequestBody ApplicantsScreeningStepDTO data){
        screeningEditService.saveEvaluation(data);
        return true;
    }

    @RequestMapping(value = "/api/setfinalresult", method = RequestMethod.POST)
    public boolean applicantsByLocation(@RequestBody ScreeningGrade grade) {
        screeningEvalService.gradeScreening(grade);
        return true;
    }

}
