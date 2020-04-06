package com.seller.panel.service;

import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Users authenticate(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if(user == null || !user.getActive() || !encoder.matches(password, user.getPassword()))
            throw getException("SP-1");
        return user;
    }

    @Transactional
    public Long createUser(Users user) {
        if(userRepository.findByEmail(user.getEmail()) != null)
            throw getException("SP-4", user.getEmail());
        user.setPassword(encoder.encode(StringUtils.trim(user.getPassword())));
        return userRepository.save(user).getId();
    }

    public Users findUserByIdAndCompanyId(Long id, Long companyId) {
        Users user = userRepository.findByIdAndCompanyId(id, companyId);
        if(user == null)
            throw getException("SP-5");
        return user;
    }

    public List<Users> findAllUsersByCompanyIdAndPageAndSize(Long companyId, Integer page, Integer size) {
        return userRepository.findAllByCompanyIdOrderByUpdatedOnDesc(companyId, PageRequest.of(page, size));
    }

}
