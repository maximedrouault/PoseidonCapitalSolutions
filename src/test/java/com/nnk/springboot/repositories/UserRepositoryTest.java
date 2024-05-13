package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void shouldReturnSavedUserWhenSaveUser() {
        User userToSave = User.builder().fullname("Fullname save test").username("Username save test").password("Password123#savetest").role("USER").build();

        User savedUser = userRepository.save(userToSave);

        assertNotNull(savedUser.getId());
        assertEquals(userToSave, savedUser);
    }

    @ParameterizedTest
    @MethodSource("provideEntriesForTest")
    void shouldThrowExceptionWhenUserToSaveIsNotValid(String fullname, String username, String  password, String role) {
        User userToSave = User.builder().fullname(fullname).username(username).password(password).role(role).build();

        assertThrows(ConstraintViolationException.class, () -> userRepository.save(userToSave));
    }

    static Stream<Arguments> provideEntriesForTest() {
        return Stream.of(
                Arguments.of(null, null, null, null),
                Arguments.of("", "", "", ""),
                Arguments.of(" ", " ", " ", " "),
                Arguments.of("p".repeat(126), "p".repeat(126), "p".repeat(126), "p".repeat(126)),
                Arguments.of("Fullname OK", "Username OK", "Pass<8", "USER"),
                Arguments.of("Fullname OK", "Username OK", "PassSup8WithoutSymbolWithoutDigit", "USER"),
                Arguments.of("Fullname OK", "Username OK", "PassWithoutSymbolWithoutDigit", "USER"),
                Arguments.of("Fullname OK", "Username OK", "PassWithSymbol#WithoutDigit", "USER"),
                Arguments.of("Fullname OK", "Username OK", "passwithouruppercase", "USER")
        );
    }

    @Test
    void shouldReturnUpdatedUserWhenUpdateUser() {
        User userToUpdate = User.builder().id(2).fullname("Fullname update test").username("Username update test").password("Password123#updatetest").role("ADMIN").build();

        User updatedUser = userRepository.save(userToUpdate);

        assertEquals(updatedUser.getId(), 2);
        assertEquals(userToUpdate, updatedUser);
    }

    @Test
    void shouldCreateNewUserWhenUserToUpdateDoesNotExist() {
        User userToUpdate = User.builder().id(9999).fullname("Fullname update test").username("Username update test").password("Password123#updatetest").role("ADMIN").build();

        User savedUser = userRepository.save(userToUpdate);

        assertNotEquals(9999, savedUser.getId());
    }

    @Test
    void shouldReturnFoundUsersWhenFindUsers() {
        List<User> foundUsers = userRepository.findAll();

        assertFalse(foundUsers.isEmpty());
    }

    @Test
    void shouldReturnFoundUserWhenFindUser() {
        int userToFindId = 2;
        Optional<User> foundUser = userRepository.findById(userToFindId);

        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getId(), 2);
    }

    @Test
    void shouldNotFindUserWhenUserDoesNotExist() {
        int userToFindId = 99;
        Optional<User> optionalFoundUser = userRepository.findById(userToFindId);

        assertTrue(optionalFoundUser.isEmpty());
    }

    @Test
    void shouldNotFindUserWhenDeleteUser() {
        int userToDeleteId = 2;
        userRepository.deleteById(userToDeleteId);
        Optional<User> optionalUser = userRepository.findById(userToDeleteId);

        assertFalse(optionalUser.isPresent());
    }
}