package com.seller.panel.controller;

import com.seller.panel.dto.User;
import com.seller.panel.dto.UserRequest;
import com.seller.panel.dto.UserResponse;
import com.seller.panel.model.Users;
import com.seller.panel.service.UserService;
import com.seller.panel.util.EndPointConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping(EndPointConstants.Users.USERS_BY_ID)
    @PreAuthorize("isAuthorized('GET','/users')")
    public ResponseEntity<UserResponse> findUserById(@NotNull @PathVariable("id") Long id) {
        User user = userService.findUserByIdAndCompanyId(id, getCompanyId());
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(EndPointConstants.Users.USERS)
    @PreAuthorize("isAuthorized('POST','/users')")
    public ResponseEntity<UserResponse> createUsers(@Valid @RequestBody UserRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw getException("SP-3");
        Long companyId = getCompanyId();
        Users user = new Users();
        BeanUtils.copyProperties(request, user);
        user.setCompanyId(companyId);
        Long userId = userService.createUser(user);
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(request, response);
        response.setId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
