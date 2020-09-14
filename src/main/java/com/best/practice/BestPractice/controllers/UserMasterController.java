package com.best.practice.BestPractice.controllers;


import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.dtos.filters.UserView;
import com.best.practice.BestPractice.dtos.validators.UserValidator;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.security.JwtUserDetailService;
import com.best.practice.BestPractice.security.LoggedInUser;
import com.best.practice.BestPractice.security.TokenProvider;
import com.best.practice.BestPractice.services.UserMasterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserMasterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMasterController.class);

    @Autowired
    UserMasterService userMasterService;

    @Autowired
    JwtUserDetailService loggedInUserDetailService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("health-ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @JsonView({UserView.Read.class})
    public ResponseEntity<UserMasterDto> login(@Validated(UserValidator.Login.class) @RequestBody UserMasterDto loginUserMasterDto) {

        UserMasterDto userMasterDto = userMasterService.getUserByName(loginUserMasterDto.getName());
        try {

            if(userMasterDto == null)
                throw new ResourceNotFound("User does not exist");

            if(!bcryptEncoder.matches(loginUserMasterDto.getPassword(),userMasterDto.getPassword()))
                throw new BadCredentialsException("Please verify your password");

            LoggedInUser loggedInUser = loggedInUserDetailService.loadUserByUsername(userMasterDto.getName());
            Authentication authentication = new UsernamePasswordAuthenticationToken(loggedInUser, "", loggedInUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(authentication);
            userMasterDto.setToken(token);

        } catch (ResourceNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
        return new ResponseEntity<>(userMasterDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    @JsonView({UserView.Read.class})
    public ResponseEntity<UserMasterDto> addUser(@Validated(UserValidator.Create.class) @RequestBody @JsonView({UserView.Create.class}) UserMasterDto createUserMasterDto) {

        UserMasterDto responseUserMasterDto = null;
        try {
            responseUserMasterDto = userMasterService.addUser(createUserMasterDto);

            LoggedInUser loggedInUser = loggedInUserDetailService.loadUserByUsername(responseUserMasterDto.getName());
            Authentication authentication = new UsernamePasswordAuthenticationToken(loggedInUser, "", loggedInUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(authentication);
            responseUserMasterDto.setToken(token);

        } catch (ResourceExist e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return new ResponseEntity<>(responseUserMasterDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-all-user", method = RequestMethod.GET)
    @JsonView({UserView.Read.class})
    public ResponseEntity<List<UserMasterDto>> getAllUser() {
        List<UserMasterDto> userDtoList = userMasterService.getUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    @JsonView({UserView.Read.class})
    public ResponseEntity<UserMasterDto> getUser(@PathVariable(value = "userName") String userName) {
        UserMasterDto responseUserMasterDto = null;
        try {
            responseUserMasterDto = userMasterService.getUserByName(userName);
        } catch (ResourceNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return new ResponseEntity<>(responseUserMasterDto, HttpStatus.OK);
    }

}
