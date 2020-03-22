package com.seller.panel.service;

import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;

    public Users authenticate(String email, String password) {
        Users user = userRepository.findByEmailAndActive(email, true);
        if(user == null || !user.getPassword().equals(password))
            throw getException("SP-6");
        return user;
    }
}
