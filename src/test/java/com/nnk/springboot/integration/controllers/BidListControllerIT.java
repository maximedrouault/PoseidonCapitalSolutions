package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.BidList;
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
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/bidList/list", "/bidList/add", "/bidList/validate", "/bidList/update", "/bidList/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnBidListWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("username", hasToString("usertest")))
                .andExpect(model().attribute("bidLists", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddNewBidListFormWhenAuthenticated()throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnBidListListWhenBidListToAddIsValid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account", "Account test")
                .param("type", "Type test")
                .param("bidQuantity", "50")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddBidListFormWhenBidListToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateBidListFormWhenBidListToUpdateIsFound() throws Exception {
        BidList expectedBidList = BidList.builder().id(5).account("Account test").type("Type test").bidQuantity(50.0).build();

        mockMvc.perform(get("/bidList/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", equalTo(expectedBidList)));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenBidListToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/bidList/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update")));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateBidListFormWhenBidListToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 5)
                .param("account", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnBidListListWhenBidListToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 5)
                        .param("account", "accountUpdateTest")
                        .param("type", "typeUpdateTest")
                        .param("bidQuantity", "30")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnBidListListWhenBidListToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/bidList/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
