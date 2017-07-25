package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningStepsController {

    @Autowired
    private ScreeningEditService screeningEditService;

    @RequestMapping(value = "/api/screeningsteps", method = RequestMethod.GET)
    public List<ScreeningStep> findByLocationId(@RequestParam String location){
        return screeningEditService.findByLocationId(location);
    }

    @RequestMapping(value = "/api/evalscreening/{applicantId}", method = RequestMethod.GET)
    public ScreeningStepEvaluationDTO findForApplicant(@PathVariable("applicantId") Integer adminId, @RequestParam("step") String stepId){
        return screeningEditService.findForApplicant(adminId, stepId);
    }
}
