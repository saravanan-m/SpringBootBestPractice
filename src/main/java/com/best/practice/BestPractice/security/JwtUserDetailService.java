package com.best.practice.BestPractice.security;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.services.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private UserMasterService userMasterService;

    @Override
    public LoggedInUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMasterDto user = userMasterService.getUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new LoggedInUser(user.getName(),user.getPassword(),getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserMasterDto user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN" ));
        return authorities;
    }
}
