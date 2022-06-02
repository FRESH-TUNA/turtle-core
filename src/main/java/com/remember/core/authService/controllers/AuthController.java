package com.remember.core.authService.controllers;

import com.remember.core.authService.ros.UserRO;
import com.remember.core.authService.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@ModelAttribute UserRO userRO) {
        authService.registerNewUserAccount(userRO);
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
    public String signupForm() { return "auth/forms/signup"; }
}
