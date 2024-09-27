package com.APIPlatform.API_platform.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(String email, String password) {}

    public UserLoginDTO() {}

}
