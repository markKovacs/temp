package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.model.dto.QuestionDTO;
import com.codecool.appsystem.admin.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/api/locations")
public class LocationsRestController {

    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @RequestMapping(value="/test/{location}", method = RequestMethod.GET)
    public List<QuestionDTO> getQuestionByLocation(@PathVariable("location") String location) {


        return null;
    }


}
