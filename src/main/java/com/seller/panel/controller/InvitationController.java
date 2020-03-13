package com.seller.panel.controller;

import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.handler.ExceptionHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1")
public class InvitationController {

    @Autowired
    private ExceptionHandler exceptionHandler;

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@NotNull @RequestBody InvitationRequest request) {
        if(StringUtils.isBlank(request.getEmail()))
            throw exceptionHandler.getException("SP-1");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}