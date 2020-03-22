package com.seller.panel.dto;

import com.seller.panel.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    @Email(message = AppConstants.INVALID)
    private String email;
    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String password;

}
