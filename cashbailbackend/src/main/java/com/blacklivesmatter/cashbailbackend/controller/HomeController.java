package com.blacklivesmatter.cashbailbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@GetMapping("/base")
	public String base(){
		return "base";
	}

	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	@GetMapping("/about")
	public String about() {
		return "aboutus";
	}
	
	@GetMapping("/resources")
	public String resources() {
		return "media";
	}
	
	
}
