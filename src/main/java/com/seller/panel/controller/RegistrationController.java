package com.seller.panel.controller;

import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
public class RegistrationController extends BaseController {
    
    @GetMapping(EndPointConstants.Registration.REGISTER)
    public ResponseEntity<Void> register(@NotNull @PathVariable("id") String id) {
        if(StringUtils.isBlank(id))
            throw getException("SP-2");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}