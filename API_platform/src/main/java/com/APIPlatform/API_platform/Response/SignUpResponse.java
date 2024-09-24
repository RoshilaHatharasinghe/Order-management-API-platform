package com.APIPlatform.API_platform.Response;

public class SignUpResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public SignUpResponse(long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SignUpResponse() {

    }

    public SignUpResponse setId(long id) {
        this.id = id;
        return this;
    }

    public SignUpResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public SignUpResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SignUpResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    @Override
    public String toString() {
        return "SignUpResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
