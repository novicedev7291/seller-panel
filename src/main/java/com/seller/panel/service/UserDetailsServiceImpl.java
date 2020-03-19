package com.seller.panel.service;

import com.seller.panel.model.Permissions;
import com.seller.panel.model.Roles;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmailAndActive(username, true);
        return new User(user.getEmail(), user.getPassword(), user.getActive(), true,
                true, true, getGrantedAuthorities(user));
    }

    private final Collection<? extends GrantedAuthority> getGrantedAuthorities(final Users user){
        Set<Roles> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Permissions> permissions = new ArrayList<>();
        for(Roles role : roles){
            permissions.addAll(role.getPermissions());
        }

        for(Permissions permission: permissions){
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return grantedAuthorities;
    }

}
