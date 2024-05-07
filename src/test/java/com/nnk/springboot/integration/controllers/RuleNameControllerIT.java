package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @ParameterizedTest
    @ValueSource(strings = {"/ruleName/list", "/ruleName/add", "/ruleName/validate", "/ruleName/update", "/ruleName/delete", "/wrongUri"})
    void shouldReturnLoginFormWhenUnauthenticated(String endpointUri) throws Exception{
        mockMvc.perform(get(endpointUri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRuleNameListWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("username", hasToString("usertest")))
                .andExpect(model().attribute("ruleNames", hasSize(greaterThan(0))));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddNewRuleNameFormWhenAuthenticated()throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRuleNameListWhenRuleNameToAddIsValid() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Name add test")
                        .param("description", "Description add test")
                        .param("json", "Json add test")
                        .param("template", "Template add test")
                        .param("sqlStr", "SQL Str add test")
                        .param("sqlPart", "SQL Part add test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnAddRuleNameFormWhenRuleNameToAddIsNotValid() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateRuleNameFormWhenRuleNameToUpdateIsFound() throws Exception {
        RuleName expectedRuleName = RuleName.builder().id(5).name("Name test").description("Description test")
                .json("Json test").template("Template test").sqlStr("SQL Str test").sqlPart("SQL Part test").build();

        mockMvc.perform(get("/ruleName/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", equalTo(expectedRuleName)));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldThrowExceptionWhenRuleNameToUpdateIsNotFound() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/ruleName/update/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update")));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnUpdateRuleNameFormWhenRuleNameToUpdateIsNotValid() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 5)
                .param("ruleName", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeHasFieldErrors("ruleName"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRuleNameListWhenRuleNameToUpdateIsValid() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 5)
                        .param("name", "Name update test")
                        .param("description", "Description update test")
                        .param("json", "Json update test")
                        .param("template", "Template update test")
                        .param("sqlStr", "SQL Str update test")
                        .param("sqlPart", "SQL Part update test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "usertest")
    void shouldReturnRuleNameListWhenRuleNameToDeleteIsFound() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
