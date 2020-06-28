package com.blacklivesmatter.cashbailbackend.service;

import com.blacklivesmatter.cashbailbackend.enums.Role;
import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.model.Cause;
import com.blacklivesmatter.cashbailbackend.model.Donation;
import com.blacklivesmatter.cashbailbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DonationServiceTests {


    @Autowired
    private  DonationService donationService;
    @Autowired
    private  AuthService authService;
    @Autowired
    private  CauseService causeService;

    @Autowired
    private UserRepository userRepository;


    private Donation cause1Donation1;
    private Donation cause1Donation2;
    private Donation cause1Donation3;
    private Donation cause2Donation1;
    private Cause cause1;
    private Cause cause2;
    private AppUser donationUser1;
    private AppUser donationUser2;

    @BeforeEach
    public void setUp() throws Exception{

        List<Donation> donations = donationService.findAllDonations();
        donations.forEach(donationService::deleteDonation);

        List<AppUser> users = userRepository.findAll();
        users.forEach(userRepository::delete);


        cause1 = new Cause();
        cause1.setAmountRequired(new BigDecimal("100.00"));
        cause1.setDateOpened(Instant.now());
        cause1.setDescription("John owes parking ticket fines. Lets's get him out.");

        cause2 = new Cause();
        cause2.setAmountRequired(new BigDecimal("100.00"));
        cause2.setDateOpened(Instant.now());
        cause2.setDescription("Adam owes parking ticket fines. Lets's get him out.");

        donationUser1 = new AppUser();
        donationUser1.setRole(Role.DONOR);
        donationUser1.setFirstName("John");
        donationUser1.setLastName("Doe");
        donationUser1.setPassword("password");
        donationUser1.setUsername("johndoe");
        donationUser1.setEmail("john.doe@gmail.com");
        donationUser1 = authService.signUp(donationUser1);



        donationUser2 = new AppUser();
        donationUser2.setRole(Role.DONOR);
        donationUser2.setFirstName("Jane");
        donationUser2.setLastName("Doe");
        donationUser2.setPassword("password");
        donationUser2.setUsername("janedoe");
        donationUser2.setEmail("jane.doe@gmail.com");
        donationUser2 = authService.signUp(donationUser2);

        cause1Donation1 = new Donation();
        cause1Donation1.setAmount(new BigDecimal("5.00"));
        cause1Donation1.setCause(cause1);
        cause1Donation1.setUser(donationUser1);

        cause1Donation2 = new Donation();
        cause1Donation2.setAmount(new BigDecimal("95.00"));
        cause1Donation2.setUser(donationUser1);
        cause1Donation2.setCause(cause1);

        cause1Donation3 = new Donation();
        cause1Donation3.setAmount(new BigDecimal("5.00"));
        cause1Donation3.setUser(donationUser2);
        cause1Donation3.setCause(cause1);

        cause2Donation1 = new Donation();
        cause2Donation1.setAmount( new BigDecimal("6.00"));
        cause2Donation1.setCause(cause2);
        cause2Donation1.setUser(donationUser1);





    }

    @Test
    public void shouldProvideCorrectTotalForCauseDonations(){
        cause1 = causeService.saveCause(cause1);
        cause1Donation1 = donationService.donate(cause1Donation1);
        cause1Donation2 =donationService.donate(cause1Donation2);

        BigDecimal total = donationService.getCauseDonationTotal(cause1);

        BigDecimal expectedTotal = new BigDecimal("100.00");

        int comparisonResult = total.compareTo(expectedTotal);
        assertEquals(0, comparisonResult);
    }


    @Test
    public void shouldProvideDonationsByUser(){
        cause1 = causeService.saveCause(cause1);
        cause1Donation1 = donationService.donate(cause1Donation1);
        cause1Donation2 = donationService.donate(cause1Donation2);
        cause1Donation3 = donationService.donate(cause1Donation3);

        List<Donation> donationsByUser1 = donationService.findDonationsByUser(donationUser1);
        assertEquals(2, donationsByUser1.size());
    }

    @Test
    public void shouldProvideDonationsByCause(){
        cause1 = causeService.saveCause(cause1);
        cause2 = causeService.saveCause(cause2);
        cause1Donation1 = donationService.donate(cause1Donation1);
        cause1Donation2 = donationService.donate(cause1Donation2);
        cause1Donation3 = donationService.donate(cause1Donation3);
        cause2Donation1 = donationService.donate(cause2Donation1);
        assertEquals(3, donationService.findDonationsByCause(cause1).size());

    }

    @Test
    public void shouldGetAllDonations(){
        assertEquals(0, donationService.findAllDonations().size());
        cause1 = causeService.saveCause(cause1);
        cause2 = causeService.saveCause(cause2);
        cause1Donation1 = donationService.donate(cause1Donation1);
        cause1Donation2 = donationService.donate(cause1Donation2);
        cause1Donation3 = donationService.donate(cause1Donation3);
        cause2Donation1 = donationService.donate(cause2Donation1);
        assertEquals(4, donationService.findAllDonations().size());

    }

    @Test
    public void shouldDeleteDonation(){
        cause1 = causeService.saveCause(cause1);
        cause2 = causeService.saveCause(cause2);
        cause1Donation1 = donationService.donate(cause1Donation1);
        cause1Donation2 = donationService.donate(cause1Donation2);
        cause1Donation3 = donationService.donate(cause1Donation3);
        cause2Donation1 = donationService.donate(cause2Donation1);
        donationService.deleteDonation(cause2Donation1);
        assertEquals(3, donationService.findAllDonations().size());

    }

}
