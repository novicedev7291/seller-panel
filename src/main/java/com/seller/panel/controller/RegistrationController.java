package com.seller.panel.controller;

import com.seller.panel.dto.RegistrationRequest;
import com.seller.panel.util.EndPointConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
public class RegistrationController extends BaseController {

    @PostMapping(EndPointConstants.Registration.REGISTER)
    public ResponseEntity<Void> register(@NotNull @RequestBody RegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
