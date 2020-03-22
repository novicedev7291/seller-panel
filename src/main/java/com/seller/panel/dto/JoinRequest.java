package com.seller.panel.dto;

import com.github.javafaker.App;
import com.seller.panel.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String email;

}
