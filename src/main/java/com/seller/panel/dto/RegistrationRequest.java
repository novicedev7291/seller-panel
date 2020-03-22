package com.seller.panel.dto;

import com.seller.panel.config.ValidPassword;
import com.seller.panel.util.AppConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public final class RegistrationRequest {

    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String name;
    private String phone;
    private String countryCode;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    @Email(message = AppConstants.INVALID)
    private String email;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String companyName;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String confirmPassword;
    @ValidPassword
    private String password;
}
