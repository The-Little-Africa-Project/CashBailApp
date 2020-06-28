package com.blacklivesmatter.cashbailbackend.service;

import com.blacklivesmatter.cashbailbackend.model.Cause;
import com.blacklivesmatter.cashbailbackend.repository.CauseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CauseService {

    private CauseRepository causeRepository;
    private DonationService donationService;



    public Cause saveCause(Cause cause){
        Optional<Cause> potentialDupe = causeRepository.findById(cause.getId());
        if(potentialDupe.isPresent())
            throw new IllegalArgumentException("Cause Already Exists");

        return causeRepository.save(cause);
    }

    public void deleteCause(Cause fromUser){
        Optional<Cause> cause = causeRepository.findById(fromUser.getId());
        if(!cause.isPresent())
            throw new IllegalArgumentException("Cause doesn't exist. Nothing to delete");

        causeRepository.delete(fromUser);
    }

    public void updateCause(Cause fromUser){
        Optional<Cause> cause = causeRepository.findById(fromUser.getId());
        if(!cause.isPresent())
            throw new IllegalArgumentException("Cause doesn't exist. Nothing to update");

        fromUser.setAmountReceived(donationService.getCauseDonationTotal(fromUser));

        causeRepository.save(fromUser);
    }

    public Cause getCause(Cause fromUser){
        Optional<Cause> cause = causeRepository.findById(fromUser.getId());
        if(!cause.isPresent())
            throw new IllegalArgumentException("Cause doesn't exist. Nothing to update");

        return cause.get();
    }



}
