package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameRepositoryTest {

    @Autowired
    private RuleNameRepository ruleNameRepository;


    @Test
    void shouldReturnSavedRuleNameWhenSaveRuleName() {
        RuleName ruleNameToSave = RuleName.builder().name("Name save test").description("Description save test").json("Json save test").template("Template save test").sqlStr("SQL Str save test").sqlPart("SQL Part save test").build();

        RuleName savedRuleName = ruleNameRepository.save(ruleNameToSave);

        assertNotNull(savedRuleName.getId());
        assertEquals(ruleNameToSave, savedRuleName);
    }

    @ParameterizedTest
    @MethodSource("provideEntriesForTest")
    void shouldThrowExceptionWhenRuleNameToSaveIsNotValid(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        RuleName ruleNameToSave = RuleName.builder().name(name).description(description).json(json).template(template).sqlStr(sqlStr).sqlPart(sqlPart).build();

        assertThrows(ConstraintViolationException.class, () -> ruleNameRepository.save(ruleNameToSave));
    }

    static Stream<Arguments> provideEntriesForTest() {
        return Stream.of(
                Arguments.of(null, null, null, null, null, null),
                Arguments.of("", "", "", "", "", ""),
                Arguments.of(" ", " ", " ", " ", " ", " "),
                Arguments.of("p".repeat(126), "p".repeat(126), "p".repeat(126), "p".repeat(513), "p".repeat(126), "p".repeat(126))
        );
    }

    @Test
    void shouldReturnUpdatedRuleNameWhenUpdateRuleName() {
        RuleName ruleNameToUpdate = RuleName.builder().id(5).name("Name update test").description("Description update test").json("Json update test").template("Template update test").sqlStr("SQL Str update test").sqlPart("SQL Part update test").build();

        RuleName updatedRuleName = ruleNameRepository.save(ruleNameToUpdate);

        assertEquals(updatedRuleName.getId(), 5);
        assertEquals(ruleNameToUpdate, updatedRuleName);
    }

    @Test
    void shouldCreateNewRuleNameWhenRuleNameToUpdateDoesNotExist() {
        RuleName ruleNameToUpdate = RuleName.builder().id(9999).name("Name update test").description("Description update test").json("Json update test").template("Template update test").sqlStr("SQL Str update test").sqlPart("SQL Part update test").build();

        RuleName savedRuleName = ruleNameRepository.save(ruleNameToUpdate);

        assertNotEquals(9999, savedRuleName.getId());
    }

    @Test
    void shouldReturnFoundRuleNamesWhenFindRuleNames() {
        List<RuleName> foundRuleNames = ruleNameRepository.findAll();

        assertFalse(foundRuleNames.isEmpty());
    }

    @Test
    void shouldReturnFoundRuleNameWhenFindRuleName() {
        int ruleNameToFindId = 5;
        Optional<RuleName> foundRuleName = ruleNameRepository.findById(ruleNameToFindId);

        assertTrue(foundRuleName.isPresent());
        assertEquals(foundRuleName.get().getId(), 5);
    }

    @Test
    void shouldNotFindRuleNameWhenRuleNameDoesNotExist() {
        int ruleNameToFindId = 99;
        Optional<RuleName> optionalFoundRuleName = ruleNameRepository.findById(ruleNameToFindId);

        assertTrue(optionalFoundRuleName.isEmpty());
    }

    @Test
    void shouldNotFindRuleNameWhenDeleteRuleName() {
        int ruleNameToDeleteId = 5;
        ruleNameRepository.deleteById(ruleNameToDeleteId);
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(ruleNameToDeleteId);

        assertFalse(optionalRuleName.isPresent());
    }
}
