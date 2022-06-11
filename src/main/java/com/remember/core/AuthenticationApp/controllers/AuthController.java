package com.remember.core.AuthenticationApp.controllers;

import com.remember.core.AuthenticationApp.dtos.UserRequestDto;
import com.remember.core.AuthenticationApp.services.UsersService;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsersService service;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String signup(@ModelAttribute @Valid UserRequestDto userRequestDto) {
        service.registerNewUserAccount(userRequestDto);
        return "redirect:/";
    }

    /*
     * forms
     */
    @GetMapping("/forms/login")
    public String loginForm(Model model, @RequestParam @Nullable String error){
        if(!Objects.isNull(error))
            model.addAttribute("error", ErrorResponse.of(ErrorCode.valueOf(error)));
        return "auth/forms/login";
    }

    @GetMapping("/forms/signup")
    public String signupForm(Model model){
        return "auth/forms/signup";
    }
}
