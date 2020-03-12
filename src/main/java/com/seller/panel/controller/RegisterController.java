package com.seller.panel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class RegisterController {

    @GetMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}