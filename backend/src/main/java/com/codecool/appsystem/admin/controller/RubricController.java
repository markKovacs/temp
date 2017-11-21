package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.Rubric;
import com.codecool.appsystem.admin.model.dto.RubricDTO;
import com.codecool.appsystem.admin.service.RubricService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/rubrics")
public class RubricController {

    @Autowired
    private RubricService rubricService;

    @RequestMapping(method = RequestMethod.GET)
    public List<RubricDTO> getRubrics(@RequestParam String criteriaId){
        return rubricService.getRubricsDTOByCriteria(criteriaId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean saveRubricList(@RequestBody List<RubricDTO> rubrics){
        log.debug(String.valueOf(rubrics.size()));
        rubricService.saveRubrics(rubrics);
        return true;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteRubric(@PathVariable Integer id){
        rubricService.deleteRubric(id);
        return true;
    }
}
