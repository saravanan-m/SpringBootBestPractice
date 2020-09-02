package com.best.practice.BestPractice.controllers;


import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.dtos.filters.UserView;
import com.best.practice.BestPractice.dtos.validators.UserValidator;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.services.UserMasterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public ResponseEntity<UserMasterDto> addUser(@Validated(UserValidator.Create.class) @RequestBody @JsonView({UserView.Create.class}) UserMasterDto createUserMasterDto) {

        UserMasterDto responseUserMasterDto = null;
        try {
            responseUserMasterDto = userMasterService.addUser(createUserMasterDto);
        } catch (ResourceExist e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return new ResponseEntity<>(responseUserMasterDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-all-user", method = RequestMethod.GET)
    public ResponseEntity<List<UserMasterDto>> getAllUser() {
        List<UserMasterDto> userDtoList = userMasterService.getUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
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
