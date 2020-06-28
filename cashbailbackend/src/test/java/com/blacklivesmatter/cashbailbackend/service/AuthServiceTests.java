package com.blacklivesmatter.cashbailbackend.service;

import com.blacklivesmatter.cashbailbackend.enums.Role;
import com.blacklivesmatter.cashbailbackend.model.AppUser;
import com.blacklivesmatter.cashbailbackend.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private  AuthService authService;
    @Autowired
    private UserRepository userRepository;

    private AppUser fromForm;
    private AppUser duplicateUserName;
    private AppUser duplicateEmail;

    @BeforeEach
    void setUp() throws Exception{
        List<AppUser> users = userRepository.findAll();

        users.forEach(appUser -> {
            userRepository.deleteById(appUser.getId());
        });

        fromForm = new AppUser();
        fromForm.setFirstName("John");
        fromForm.setLastName("Doe");
        fromForm.setEmail("john.doe@gmail.com");
        fromForm.setUsername("johndoe");
        fromForm.setPassword("password");
        duplicateUserName = new AppUser();
        duplicateUserName.setFirstName("John");
        duplicateUserName.setLastName("Doe");
        duplicateUserName.setEmail("john.doe1@gmail.com");
        duplicateUserName.setUsername("johndoe");
        duplicateUserName.setPassword("password");
        duplicateEmail = new AppUser();
        duplicateEmail.setFirstName("John");
        duplicateEmail.setLastName("Doe");
        duplicateEmail.setEmail("john.doe@gmail.com");
        duplicateEmail.setUsername("johndoe1");
        duplicateEmail.setPassword("password");

    }


    @Test
    public void signUpTest() throws Exception{

        AppUser fromRepo = authService.signUp(fromForm);
        assertNotNull(fromRepo.getId());
        assertNotNull(fromRepo.getCreated());
        assertTrue(fromRepo.isEnabled());
        assertEquals(Role.DONOR, fromRepo.getRole());

    }

    @Test
    public void shouldThrowIllegalArgWhenUserAlreadyExists(){

        authService.signUp(fromForm);
        assertThrows(IllegalArgumentException.class, ()->{
            authService.signUp(duplicateUserName);
        });

    }


    @Test
    public void shouldThrowIllegalArgWhenEmailAlreadyExists(){
         authService.signUp(fromForm);
        assertThrows(IllegalArgumentException.class, ()->{
            authService.signUp(duplicateUserName);
        });
    }

}
