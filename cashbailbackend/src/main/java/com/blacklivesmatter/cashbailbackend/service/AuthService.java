package com.blacklivesmatter.cashbailbackend.service;

import com.blacklivesmatter.cashbailbackend.enums.Role;
import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AppUser signUp(AppUser fromForm){
        AppUser user = new AppUser();
        user.setFirstName(fromForm.getFirstName());
        user.setLastName(fromForm.getLastName());
        user.setUsername(fromForm.getUsername());
        user.setEmail(fromForm.getEmail());
        user.setPassword(passwordEncoder.encode(fromForm.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(true);
        user.setRole(Role.DONOR);

        Optional<AppUser> byUsername = userRepository.findByUsername(fromForm.getUsername());


        if( byUsername.isPresent() )
            throw new IllegalArgumentException("user already exists");

        Optional<AppUser> byEmail = userRepository.findByEmail(fromForm.getEmail());

        if(byEmail.isPresent())
            throw new IllegalArgumentException("email address already associated with user");


        return userRepository.save(user);
    }
    
    public void createAdminUser(AppUser newUser) {
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		newUser.setRole(Role.ADMIN);
		
		this.userRepository.save(newUser);
	}
	
	public List<AppUser> getAdminUsers() {
		return this.userRepository.findByRole(Role.ADMIN);
	}


    public boolean isAdmin(User user) {
    	if(user == null) {
    		return false;
    	}
    	
    	Optional<AppUser> appUser = this.userRepository.findByUsername(user.getUsername());
    	return appUser.isPresent() && appUser.get().getRole() == Role.ADMIN;
    }
}
