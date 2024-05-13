package com.nnk.springboot.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnLoginViewWhenLoginEndpointIsCalled() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void shouldReturnLoginViewWhenSecureArticleDetailsIsCalledByUnauthenticated() throws Exception {
        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest", roles = "USER")
    void shouldReturnUnauthorizedWhenSecureArticleDetailsIsCalledByUser() throws Exception {
        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUserListWhenSecureArticleDetailsIsCalledWithAdminUser() throws Exception {
        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void shouldReturnLoginViewWhenErrorEndpointIsCalledByUnauthenticated() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest", roles = "USER")
    void shouldReturnUnauthorizedViewWhenErrorEndpointIsCalledByAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg", "username"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnUnauthorizedViewWhenErrorEndpointIsCalledByAdminUser() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg", "username"));
    }
}
