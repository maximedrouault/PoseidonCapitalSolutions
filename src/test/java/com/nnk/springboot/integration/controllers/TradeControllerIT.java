package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.Trade;
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
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/trade/list", "/trade/add", "/trade/validate", "/trade/update", "/trade/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnTradeListWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("username", hasToString("usertest")))
                .andExpect(model().attribute("trades", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddNewTradeFormWhenAuthenticated()throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnTradeListWhenTradeToAddIsValid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account add test")
                        .param("type", "Type add test")
                        .param("buyQuantity", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddTradeFormWhenTradeToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateTradeFormWhenTradeToUpdateIsFound() throws Exception {
        Trade expectedtrade = Trade.builder().id(5).account("Account test").type("Type test").buyQuantity(30.0).build();

        mockMvc.perform(get("/trade/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", equalTo(expectedtrade)));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenTradeToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/trade/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update")));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateTradeFormWhenTradeToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 5)
                .param("trade", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrors("trade"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnTradeListWhenTradeToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 5)
                        .param("account", "Account update test")
                        .param("type", "Type update test")
                        .param("buyQuantity", "50")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnTradeListWhenTradeToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenTradeToDeleteIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/trade/delete/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/delete")));
    }
}
