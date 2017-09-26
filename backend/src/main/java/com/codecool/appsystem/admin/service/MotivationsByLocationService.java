package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivationsByLocationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MotivationsUtilService motivationsUtilService;

    public List<MotivationDTO> applicantsByLocation(String locationId) {

        List<User> userList = userRepository.findByLocationId(locationId);

        return motivationsUtilService.getUngradedUsers(userList, locationId);
    }
}
