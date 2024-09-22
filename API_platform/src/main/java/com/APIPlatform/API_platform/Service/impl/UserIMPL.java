package com.APIPlatform.API_platform.Service.impl;

import com.APIPlatform.API_platform.DTO.UserDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Repo.UserRepo;
import com.APIPlatform.API_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUser_id(),
                userDTO.getFirst_name(),
                userDTO.getLast_name(),
                userDTO.getEmail(),
//                this.passwordEncoder.encode(userDTO.getPassword())
                userDTO.getPassword()

        );
        userRepo.save(user);

        return user.getFirst_name();
    }
}
