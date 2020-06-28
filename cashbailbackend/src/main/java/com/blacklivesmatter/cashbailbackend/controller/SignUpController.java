package com.blacklivesmatter.cashbailbackend.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class SignUpController {

	private final AuthService authService;

    @GetMapping("/register")
    public String showSignUp(@ModelAttribute("donor") AppUser donor){
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("donor") AppUser fromForm, BindingResult bindingResult){
    	if(bindingResult.hasErrors()) {
    		return "/register";
    	}
    	
        authService.signUp(fromForm);

        return "redirect:/";
    }

}
