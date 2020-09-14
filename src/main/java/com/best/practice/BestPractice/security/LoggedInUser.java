package com.best.practice.BestPractice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedInUser extends User {


    public LoggedInUser(String username,String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
