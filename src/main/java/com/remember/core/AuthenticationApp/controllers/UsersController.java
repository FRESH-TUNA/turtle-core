package com.remember.core.AuthenticationApp.controllers;

import com.remember.core.AuthenticationApp.dtos.UserRequestDto;
import com.remember.core.AuthenticationApp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService service;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@ModelAttribute @Valid UserRequestDto userRequestDto) {
        service.registerNewUserAccount(userRequestDto);
        return "redirect:/";
    }

    @GetMapping("/forms/create")
    public String signupForm(Model model){
        return "auth/forms/signup";
    }
}
