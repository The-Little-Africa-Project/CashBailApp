package com.blacklivesmatter.cashbailbackend.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.blacklivesmatter.cashbailbackend.model.Cause;
import com.blacklivesmatter.cashbailbackend.repository.CauseRepository;
import com.blacklivesmatter.cashbailbackend.service.CauseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class CauseController {

	private final CauseRepository causeRepository;
	private final CauseService causeService;
	
	@GetMapping("/causes")
	public String showCauses() {
		return "causes";
	}
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@GetMapping("/cause/new")
	public ModelAndView newCause(@AuthenticationPrincipal User user) {
		Cause newCause = new Cause();
		newCause.setDonations(new ArrayList<>());
		
		return new ModelAndView("create_cause")
				.addObject("cause", newCause);
	}
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@PostMapping("/cause")
	public String createNewCause(@Valid @ModelAttribute("cause") Cause cause, BindingResult bindingResult, @AuthenticationPrincipal User user) {
		if(bindingResult.hasErrors()) {
			return "cause";
		}
		
		cause.setAmountReceived(BigDecimal.ZERO);
		cause.setAmountRequired(BigDecimal.ZERO);
		cause.setDateOpened(Instant.now());
		cause.setDonations(new ArrayList<>());
		
		this.causeService.saveCause(cause);
		
		return "redirect:/causes";
	}
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@GetMapping("/cause/{id}/edit")
	public ModelAndView showEditCause(@PathVariable Long id, @AuthenticationPrincipal User user) {
		Optional<Cause> causeOptional = this.causeRepository.findById(id);
		
		if(!causeOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
		}
		
		return new ModelAndView("edit_cause")
				.addObject("cause", causeOptional.get());
	}
	
	@PreAuthorize("@authService.isAdmin(#user)")
	@PostMapping("/cause/{id}")
	public String saveCause(@PathVariable Long id, @Valid @ModelAttribute("cause") Cause updatedCause, BindingResult bindingResult, @AuthenticationPrincipal User user) {
		Optional<Cause> causeOptional = this.causeRepository.findById(id);
		
		if(!causeOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
		}
		
		if(bindingResult.hasErrors()) {
			return String.format("/cause/%d/edit", id);
		}
		
		Cause cause = causeOptional.get();
		cause.setDescription(updatedCause.getDescription());
		
		this.causeService.updateCause(cause);
		
		return "redirect:/causes";
	}
}

