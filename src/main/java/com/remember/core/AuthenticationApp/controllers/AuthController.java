package com.remember.core.AuthenticationApp.controllers;

import com.remember.core.AuthenticationApp.dtos.UserRequestDto;
import com.remember.core.AuthenticationApp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@ModelAttribute @Valid UserRequestDto userRequestDto) {
        authService.registerNewUserAccount(userRequestDto);
        return "redirect:/";
    }


    /*
     * forms
     */
    @GetMapping("/forms/login")
    public String login(){
        return "auth/forms/login";
    }

    @GetMapping("/forms/signup")
    public String signupForm(Model model){
        return "auth/forms/signup";
    }
}
