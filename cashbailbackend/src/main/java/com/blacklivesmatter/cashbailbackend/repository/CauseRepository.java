package com.blacklivesmatter.cashbailbackend.repository;

import com.blacklivesmatter.cashbailbackend.model.Cause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends JpaRepository<Cause, Long> {
}
