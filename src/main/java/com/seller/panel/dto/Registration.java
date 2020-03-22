package com.seller.panel.dto;

import lombok.Data;

@Data
public class Registration {

    private String name;
    private String phone;
    private String countryCode;
    private String email;
    private String companyName;
    private String confirmPassword;
    private String password;

}
