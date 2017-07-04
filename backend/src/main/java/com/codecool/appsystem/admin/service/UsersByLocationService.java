package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.UserDTO;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersByLocationService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> applicantsByLocation(String locationId) {

        List<User> userList = userRepository.findByLocationId(locationId);
        List<UserDTO> userDtoList = new ArrayList<>();

        for (User user: userList) {
            UserDTO uDto = UserDTO.buildSuccess();
            uDto.setAdminId(user.getAdminId());
            uDto.setGivenName(user.getGivenName());
            uDto.setMiddleName(user.getMiddleName());
            uDto.setFamilyName(user.getFamilyName());
            userDtoList.add(uDto);
        }

        return userDtoList;
    }
}
