package com.seller.panel.dto;

import com.seller.panel.util.AppConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {

    @NotBlank(message = AppConstants.MUSTNOTBEEMPTY)
    private String name;
    private String description;
    private Long parentId;

}
