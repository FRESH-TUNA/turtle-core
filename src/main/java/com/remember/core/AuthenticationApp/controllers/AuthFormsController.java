package com.remember.core.AuthenticationApp.controllers;

import com.remember.core.exceptions.ErrorCode;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class AuthFormsController {
    /*
     * forms
     */
    @GetMapping("/forms/login")
    public String login(Model model, @RequestParam @Nullable String error){
        if(!Objects.isNull(error))
            model.addAttribute("error", ErrorCode.valueOf(error).getMessage());
        return "auth/forms/login";
    }
}
