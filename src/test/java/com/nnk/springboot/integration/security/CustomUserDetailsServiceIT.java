package com.nnk.springboot.integration.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CustomUserDetailsServiceIT {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void shouldLoadUserByUsernameWhenUserExists() {
        User expectedUser = User.builder().username("usertest").password("$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa").role("USER").build();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("usertest");

        assertEquals(expectedUser.getUsername(), userDetails.getUsername());
        assertEquals(expectedUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + expectedUser.getRole())));
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
          assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("unknownuser"));
    }
}
