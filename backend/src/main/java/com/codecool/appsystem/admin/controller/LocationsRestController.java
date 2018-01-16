package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/locations")
public class LocationsRestController {

    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Location getLocation(@PathVariable("id") String id) {
        return locationRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean save(@RequestBody Location location) {
        locationRepository.saveAndFlush(location);
        return true;
    }

}
