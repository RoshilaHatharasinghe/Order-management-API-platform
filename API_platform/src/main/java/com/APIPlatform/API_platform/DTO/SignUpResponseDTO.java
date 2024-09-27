package com.APIPlatform.API_platform.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public SignUpResponseDTO(long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SignUpResponseDTO() {

    }

    public SignUpResponseDTO setId(long id) {
        this.id = id;
        return this;
    }

    public SignUpResponseDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public SignUpResponseDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SignUpResponseDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "SignUpResponseDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
