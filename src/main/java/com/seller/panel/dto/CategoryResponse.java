package com.seller.panel.dto;

import com.seller.panel.util.AppConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private KeyNameLong parent;

}
