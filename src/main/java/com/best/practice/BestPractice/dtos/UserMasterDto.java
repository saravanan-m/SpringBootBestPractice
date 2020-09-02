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
    @NotBlank(message = "firstName cannot be null",groups = {UserValidator.Create.class})
    private String name;
}
