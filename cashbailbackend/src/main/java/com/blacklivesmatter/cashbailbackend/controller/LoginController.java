package com.blacklivesmatter.cashbailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@ResponseBody
	@GetMapping("/encode")
	public String encode(String pw) {
		return this.passwordEncoder.encode(pw);
	}
}
