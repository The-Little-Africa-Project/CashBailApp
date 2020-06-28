package com.blacklivesmatter.cashbailbackend.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.blacklivesmatter.cashbailbackend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "must include username")
	private String username;

	@NotBlank(message = "must include password")
	private String password;

	@NotBlank(message = "must include firstName")
	private String firstName;

	@NotBlank(message = "must include lastName")
	private String lastName;

	@NotBlank(message = "must include email address")
	@Email
	private String email;

	private boolean enabled;

	private Instant created;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(targetEntity = Donation.class)
	List<Donation> donationsMade;
	
}
