package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        User user = userRepository.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}

