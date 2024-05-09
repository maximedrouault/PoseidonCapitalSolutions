package com.nnk.springboot.unit.security;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void shouldLoadUserByUsernameWhenUserExists() {
        User expectedUser = User.builder().username("testuser").password("testpassword").role("USER").build();
        when(userRepository.findByUsername("testuser")).thenReturn(expectedUser);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertEquals(expectedUser.getUsername(), userDetails.getUsername());
        assertEquals(expectedUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + expectedUser.getRole())));
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("testuser"));
    }
}
