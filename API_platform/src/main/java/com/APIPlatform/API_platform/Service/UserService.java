package com.APIPlatform.API_platform.Service;

import com.APIPlatform.API_platform.DTO.SignupRequestDTO;
import com.APIPlatform.API_platform.DTO.UserLoginDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Exception.UserAlreadyExistsException;
import com.APIPlatform.API_platform.Repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations in the application.

 * This class provides methods to:
 * - Add a new user to the system.
 * - Authenticate and log in an existing user.

 * It interacts with `UserRepo` for database operations and utilizes
 * `PasswordEncoder` for password hashing and `AuthenticationManager` for authenticating user credentials.
 */
@Service
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepo userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder)
    {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user to the system after checking for existing email.
     *
     * @param signupRequestDTO the signup request containing user details.
     * @return the created User object.
     * @throws UserAlreadyExistsException if a user with the same email already exists.
     */
    public User addUser(SignupRequestDTO signupRequestDTO) throws UserAlreadyExistsException {
        if(userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        else{
            User user = new User(
                    signupRequestDTO.getId(),
                    signupRequestDTO.getFirstName(),
                    signupRequestDTO.getLastName(),
                    signupRequestDTO.getEmail(),
                    this.passwordEncoder.encode(signupRequestDTO.getPassword())
            );
            return userRepository.save(user);
        }
    }

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param userLoginDTO the login request containing user credentials.
     * @return the authenticated User object.
     * @throws AuthenticationException if authentication fails due to invalid credentials.
     */
    public User loginUser(UserLoginDTO userLoginDTO) throws AuthenticationException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword()
                )
        );

        return userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow();
    }

}
