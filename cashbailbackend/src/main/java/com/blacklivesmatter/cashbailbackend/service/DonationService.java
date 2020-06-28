package com.blacklivesmatter.cashbailbackend.service;


import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.model.Cause;
import com.blacklivesmatter.cashbailbackend.model.Donation;
import com.blacklivesmatter.cashbailbackend.repository.DonationRepository;
import com.blacklivesmatter.cashbailbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;


    public BigDecimal getCauseDonationTotal(Cause cause){
        List<Donation> donationsForCause = donationRepository.findByCause(cause);

        return donationsForCause.stream().map(Donation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public List<Donation> findAllDonations(){
        return donationRepository.findAll();
    }

    public void deleteDonation(Donation fromUser){
        Optional<Donation> donation = donationRepository.findById(fromUser.getId());
        if(!donation.isPresent())
            throw new IllegalArgumentException("user to delete not found");


        donationRepository.delete(donation.get());
    }


    public List<Donation> findDonationsByCause(Cause cause){
       return donationRepository.findByCause(cause);
    }


    public List<Donation> findDonationsByUser(AppUser user){
        Optional<AppUser> userToLocate = userRepository.findById(user.getId());
        if(!userToLocate.isPresent())
            throw new IllegalArgumentException("user not found");


        return donationRepository.findByUser(user);
    }


    public Donation donate(Donation donation){
        if(donation.getAmount().compareTo(new BigDecimal("5.00")) < 0)
            throw new IllegalArgumentException("Donation should be greater that $5");



        return donationRepository.save(donation);
    }


}
