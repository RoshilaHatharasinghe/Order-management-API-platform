package com.APIPlatform.API_platform.Service;

import com.APIPlatform.API_platform.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public String addUser(UserDTO userDTO);
}
