package com.seller.panel.dto;

import com.seller.panel.util.AppConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationRequest {

    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String firstName;
    private String lastName;
    private String phone;
    private String countryCode;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    @Pattern(message = AppConstants.INVALID, regexp = AppConstants.EMAIL_REGEX)
    private String email;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String companyName;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String confirmPassword;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String password;

}
