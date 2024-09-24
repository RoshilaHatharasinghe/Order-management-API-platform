package com.APIPlatform.API_platform.Controller;

import com.APIPlatform.API_platform.DTO.UserDTO;
import com.APIPlatform.API_platform.DTO.UserLoginDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Response.LoginResponse;
import com.APIPlatform.API_platform.Response.SignUpResponse;
import com.APIPlatform.API_platform.Service.JWTService;
import com.APIPlatform.API_platform.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/user")
@RestController
@CrossOrigin

public class UserController {

    private final JWTService jwtService;
    private final UserService userService;

    // Constructor-based dependency injection for JWTService and UserService
    public UserController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Endpoint to handle user sign-up.
     * Receives a UserDTO object from the request body and adds the user to the system.
     *
     * @param userDTO User details for sign-up.
     * @return ResponseEntity containing the created User entity.
     */
    @PostMapping(path = "/signup")
    public ResponseEntity<User> signupUser(@RequestBody UserDTO userDTO)
    {
        User signedUpUser = userService.addUser(userDTO); // Call the user service to add a new user
        return ResponseEntity.ok(signedUpUser); // Return the newly created user in the response body
    }

    /**
     * Endpoint to handle user login.
     * Receives UserLoginDTO object from the request body, authenticates the user,
     * generates a JWT token if login is successful, and returns the token with expiration time.
     *
     * @param userLoginDTO Login credentials (email and password).
     * @return ResponseEntity containing the LoginResponse with the JWT token and expiration time.
     */
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginDTO userLoginDTO)
    {
        User loggedUser = userService.loginUser(userLoginDTO);  //Authenticate the user
        String jwtToken = jwtService.generateToken(loggedUser); //Generate the JWT token

        // Create a response object containing the token and expiration time
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
