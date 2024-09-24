package com.APIPlatform.API_platform.Service;

import com.APIPlatform.API_platform.DTO.UserDTO;
import com.APIPlatform.API_platform.DTO.UserLoginDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getFirst_name(),
                userDTO.getLast_name(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())

        );
        return userRepository.save(user);
    }

    public User loginUser(UserLoginDTO userLoginDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword()
                )
        );

        return userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow();
    }

}
