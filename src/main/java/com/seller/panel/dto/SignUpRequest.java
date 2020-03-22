package com.seller.panel.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {

    @NotBlank(message = "Must not be empty")
    private String firstName;
    private String lastName;
    private String phone;
    private String countryCode;
    @NotBlank(message = "Must not be empty")
    private String email;
    @NotBlank(message = "Must not be empty")
    private String companyName;
    @NotBlank(message = "Must not be empty")
    private String confirmPassword;
    @NotBlank(message = "Must not be empty")
    private String password;

}
