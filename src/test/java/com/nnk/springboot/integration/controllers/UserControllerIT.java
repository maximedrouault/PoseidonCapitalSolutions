package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.User;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/user/list", "/user/add", "/user/validate", "/user/update", "/user/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user/list", "/user/add", "/user/validate", "/user/update", "/user/delete"})
    @WithMockUser(username = "usertest", roles = "USER")
    void shouldReturnUnauthorizedWhenAuthenticatedWithRoleUser(String endpointUri) throws Exception {
        mockMvc.perform(get(endpointUri))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserListWhenAuthenticatedWithRoleAdmin() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("username", hasToString("admin")))
                .andExpect(model().attribute("users", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnAddNewUserFormWhenAdminAuthenticated()throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserListWhenUserToAddIsValid() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "Fullname add test")
                        .param("username", "Username add test")
                        .param("password", "PasswordTest123#")
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnAddUserFormWhenUserToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUpdateUserFormWhenUserToUpdateIsFound() throws Exception {
        User expectedUser = User.builder().id(2).fullname("User Test").username("usertest").password("").role("USER").build();

        mockMvc.perform(get("/user/update/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", equalTo(expectedUser)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldThrowExceptionWhenUserToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/user/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUpdateUserFormWhenUserToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 2)
                .param("fullname", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserListWhenUserToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 2)
                        .param("fullname", "Fullname update test")
                        .param("username", "Username update test")
                        .param("password", "PasswordUpdateTest123#")
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserListWhenUserToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/user/delete/{id}", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldThrowExceptionWhenUserToDeleteIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/user/delete/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("user/delete")));
    }
}
