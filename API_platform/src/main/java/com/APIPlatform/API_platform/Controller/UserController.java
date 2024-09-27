package com.APIPlatform.API_platform.Controller;

import com.APIPlatform.API_platform.DTO.LoginResponseDTO;
import com.APIPlatform.API_platform.DTO.SignupRequestDTO;
import com.APIPlatform.API_platform.DTO.UserLoginDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.DTO.SignUpResponseDTO;
import com.APIPlatform.API_platform.Response.SuccessResponse;
import com.APIPlatform.API_platform.Service.JWTService;
import com.APIPlatform.API_platform.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
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
     * Endpoint for user signup.
     * Handles the creation of a new user and returns the user's signup details.
     */
    @PostMapping(path = "/signup", produces = "application/json")
    public ResponseEntity<SuccessResponse> signupUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        User newUser = userService.addUser(signupRequestDTO);

        // Build the signup response object
        SignUpResponseDTO signUpResponse = new SignUpResponseDTO()
                .setId(newUser.getId())
                .setEmail(newUser.getEmail())
                .setFirstName(newUser.getFirstName())
                .setLastName(newUser.getLastName());

        SuccessResponse response = new SuccessResponse(signUpResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for user login.
     * Authenticates the user and generates a JWT token for further requests.
     */
    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<SuccessResponse> loginUser(@RequestBody UserLoginDTO userLoginDTO)
    {
        User loggedUser = userService.loginUser(userLoginDTO);

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(loggedUser);

        // Build the login response object with token and expiration time
        LoginResponseDTO loginResponse = new LoginResponseDTO()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

        SuccessResponse successResponse = new SuccessResponse(loginResponse);
        return ResponseEntity.ok(successResponse);
    }
}
