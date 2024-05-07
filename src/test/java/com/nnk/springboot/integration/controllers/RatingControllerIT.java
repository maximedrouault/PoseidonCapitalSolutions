package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.Rating;
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
public class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/rating/list", "/rating/add", "/rating/validate", "/rating/update", "/rating/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRatingListWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("username", hasToString("usertest")))
                .andExpect(model().attribute("ratings", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddNewRatingFormWhenAuthenticated()throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRatingListWhenRatingToAddIsValid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "newMoodysRating")
                .param("sandPRating", "newSandPRating")
                .param("fitchRating", "newFitchRating")
                .param("orderNumber", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddRatingFormWhenRatingToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateRatingFormWhenRatingToUpdateIsFound() throws Exception {
        Rating expectedRating = Rating.builder().id(5).moodysRating("Moodys Rating test").sandPRating("Sand PRating test").fitchRating("Fitch Rating test").orderNumber(5).build();

        mockMvc.perform(get("/rating/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", equalTo(expectedRating)));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenRatingToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/rating/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update")));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateRatingFormWhenRatingToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 5)
                .param("moodysRating", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRatingListWhenRatingToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 5)
                        .param("moodysRating", "moodysRatingUpdateTest")
                        .param("sandPRating", "sandPRatingUpdateTest")
                        .param("fitchRating", "fitchRatingUpdateTest")
                        .param("orderNumber", "6")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRatingListWhenRatingToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenRatingToDeleteIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/rating/delete/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/delete")));
    }
}
