package com.omnirio.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        //TODO make a call to DB to retrieve user & it's role
        boolean isUserExist = userService.isUserExist(user);
        if(!isUserExist){
            throw new UsernameNotFoundException("User: "+user+" doesn't exist");
        }
        return new User(user,"password",new ArrayList<>());
    }
}
