package com.best.practice.BestPractice.dtos;


import com.best.practice.BestPractice.dtos.filters.AccountView;
import com.best.practice.BestPractice.dtos.filters.BaseView;
import com.best.practice.BestPractice.dtos.filters.UserView;
import com.best.practice.BestPractice.dtos.validators.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AccountDto {

    @JsonView(AccountView.Read.class)
    private  Long id;

    @JsonView({AccountView.Create.class,AccountView.Read.class})
    @NotBlank(message = "Balance cant be null",groups = {AccountView.Create.class})
    private int balance;


}
