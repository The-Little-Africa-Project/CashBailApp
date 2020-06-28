package com.blacklivesmatter.cashbailbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklivesmatter.cashbailbackend.enums.Role;
import com.blacklivesmatter.cashbailbackend.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	Optional<AppUser> findByUsername(String username);

	Optional<AppUser> findByEmail(String email);
	
	List<AppUser> findByRole(Role role);
}
