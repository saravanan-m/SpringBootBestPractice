package com.best.practice.BestPractice.dtos;


import com.best.practice.BestPractice.dtos.filters.UserView;
import com.best.practice.BestPractice.dtos.validators.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class UserMasterDto {

    @JsonView(UserView.Read.class)
    private  Long id;

    @JsonView({UserView.Create.class , UserView.Update.class , UserView.Read.class})
    @NotBlank(message = "Name cannot be null",groups = {UserValidator.Create.class,UserValidator.Login.class})
    private String name;

    @NotBlank(message = "Password cannot be null",groups = {UserValidator.Create.class,UserValidator.Login.class})
    @JsonView({UserView.Create.class , UserView.Update.class})
    private String password;

    @JsonView({UserView.Read.class , UserView.Update.class})
    private String desc;

    @JsonView({UserView.Read.class , UserView.Update.class})
    private String token;

}
