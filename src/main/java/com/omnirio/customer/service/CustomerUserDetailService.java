package com.omnirio.customer.service;

import com.omnirio.customer.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        //TODO make a call to DB to retrieve user & it's role
        UserEntity userEntity = userService.getAuthUser(user);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User: " + user + " doesn't exist");
        }
        String role = userEntity.getRoleEntity().getRoleType().name();
        return new User(user, "password", getAuthorities(role));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleName) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        return authorities;
    }

}
