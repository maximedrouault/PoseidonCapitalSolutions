package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.CurvePoint;
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
public class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/curvePoint/list", "/curvePoint/add", "/curvePoint/validate", "/curvePoint/update", "/curvePoint/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnCurvePointListWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("username", hasToString("usertest")))
                .andExpect(model().attribute("curvePoints", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddNewCurvePointFormWhenAuthenticated()throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnCurvePointListWhenCurvePointToAddIsValid() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "15")
                .param("term", "10")
                .param("value", "20")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddCurvePointFormWhenCurvePointToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateCurvePointFormWhenCurvePointToUpdateIsFound() throws Exception {
        CurvePoint expectedCurvepoint = CurvePoint.builder().id(5).curveId(10).term(50.0).value(30.0).build();

        mockMvc.perform(get("/curvePoint/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", equalTo(expectedCurvepoint)));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenCurvePointToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/curvePoint/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update")));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateCurvePointFormWhenCurvePointToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 5)
                .param("curveId", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnCurvePointListWhenCurvePointToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 5)
                        .param("curveId", "15")
                        .param("term", "25")
                        .param("value", "35")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnCurvePointListWhenCurvePointToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenCurvePointToDeleteIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/curvePoint/delete/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/delete")));
    }
}
