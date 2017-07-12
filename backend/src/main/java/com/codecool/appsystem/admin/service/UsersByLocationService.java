package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.UserDTO;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersByLocationService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> applicantsByLocation(String locationId) {

        List<User> userList = userRepository.findByLocationId(locationId);

        return userList
                .stream()
                .map(this::transform)
                .collect(Collectors.toList());

    }

    private UserDTO transform(User user){
        return UserDTO.builder()
                .adminId(user.getAdminId())
                .givenName(user.getGivenName())
                .middleName(user.getMiddleName())
                .familyName(user.getFamilyName())
                .build();
    }
}
