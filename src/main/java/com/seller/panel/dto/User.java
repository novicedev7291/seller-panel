package com.seller.panel.dto;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String countryCode;
    private Boolean active;

}
