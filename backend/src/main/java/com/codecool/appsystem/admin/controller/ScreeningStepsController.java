package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import com.codecool.appsystem.admin.service.ScreeningEvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ScreeningStepEvaluationDTO findForApplicant(@PathVariable("applicantId") Integer id, @RequestParam("step") String stepId){
        return screeningEditService.findForApplicant(id, stepId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/evalscreening", method = RequestMethod.POST)
    public void saveEvaluation(@RequestBody ApplicantsScreeningStep data){
        screeningEditService.saveEvaluation(data);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/setfinalresult", method = RequestMethod.POST)
    public void applicantsByLocation(@RequestBody ScreeningGrade grade) {
        screeningEvalService.gradeScreening(grade);
    }

}
