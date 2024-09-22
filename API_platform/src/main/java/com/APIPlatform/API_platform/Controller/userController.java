package com.APIPlatform.API_platform.Controller;

import com.APIPlatform.API_platform.DTO.UserDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/user")
@RestController
@CrossOrigin

public class userController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDTO userDTO)
    {
        return userService.addUser(userDTO);
    }
}
