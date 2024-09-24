package com.APIPlatform.API_platform.DTO;

import lombok.Getter;

@Getter
public class UserDTO {

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public UserDTO(long id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", firstName='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
