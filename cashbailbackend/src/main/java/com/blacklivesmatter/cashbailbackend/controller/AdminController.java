package com.blacklivesmatter.cashbailbackend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.service.AuthService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class AdminController {

	private final AuthService authService;
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@GetMapping("/admin")
	public ModelAndView showAdminPage(@AuthenticationPrincipal User user) {
		List<AppUser> adminUsers = this.authService.getAdminUsers();
		
		return new ModelAndView("admin")
				.addObject("user", new AppUser())
				.addObject("adminUsers", adminUsers);
	}
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@PostMapping("/admin/user")
	public String createAdminUser(@Valid @ModelAttribute("user") AppUser newUser, BindingResult bindingResult, @AuthenticationPrincipal User user) {
		if(bindingResult.hasErrors()) {
			return "admin";
		}
		
		this.authService.createAdminUser(newUser);
		
		return "redirect:/admin";
	}
}
