package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.UserDTO;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotivationsByLocationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MotivationsUtilService motivationsUtilService;

    public List<UserDTO> applicantsByLocation(String locationId) {

        List<User> userList = userRepository.findByLocationId(locationId);

        List<User> ungradedMotivations = motivationsUtilService.getUngradedUsers(userList);

        // pass user list to a service where those without graded mot-vid gets selected and returned

        return ungradedMotivations
                .stream()
                .map(this::transform)
                .collect(Collectors.toList());

    }

    private UserDTO transform(User user){
        return UserDTO.builder()
                .adminId(user.getAdminId())
                .fullName(user.getFullName())
                .build();
    }
}
