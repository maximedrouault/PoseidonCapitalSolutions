package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;


    @Test
    void shouldReturnSavedTradeWhenSaveTrade() {
        Trade tradeToSave = Trade.builder().account("Account save test").type("Type save test").buyQuantity(10d).build();

        Trade savedTrade = tradeRepository.save(tradeToSave);

        assertNotNull(savedTrade.getId());
        assertEquals(tradeToSave, savedTrade);
    }

    @ParameterizedTest
    @CsvSource({
            ",,",
            "'', '', -1.0",
            "' ', ' ', 0.0",
            "'...............................', '...............................', '1.7976931348623157E309'"
    })
    void shouldThrowExceptionWhenTradeToSaveIsNotValid(String account, String type, Double buyQuantity) {
        Trade tradeToSave = Trade.builder().account(account).type(type).buyQuantity(buyQuantity).build();

        assertThrows(ConstraintViolationException.class, () -> tradeRepository.save(tradeToSave));
    }

    @Test
    void shouldReturnUpdatedTradeWhenUpdateTrade() {
        Trade tradeToUpdate = Trade.builder().id(5).account("Account update test").type("Type update test").buyQuantity(20d).build();

        Trade updatedTrade = tradeRepository.save(tradeToUpdate);

        assertEquals(updatedTrade.getId(), 5);
        assertEquals(tradeToUpdate, updatedTrade);
    }

    @Test
    void shouldCreateNewTradeWhenTradeToUpdateDoesNotExist() {
        Trade tradeToUpdate = Trade.builder().id(9999).account("Account update test").type("Type update test").buyQuantity(20d).build();

        Trade savedTrade = tradeRepository.save(tradeToUpdate);

        assertNotEquals(9999, savedTrade.getId());
    }

    @Test
    void shouldReturnFoundTradesWhenFindTrades() {
        List<Trade> foundTrades = tradeRepository.findAll();

        assertFalse(foundTrades.isEmpty());
    }

    @Test
    void shouldReturnFoundTradeWhenFindTrade() {
        int tradeToFindId = 5;
        Optional<Trade> foundTrade = tradeRepository.findById(tradeToFindId);

        assertTrue(foundTrade.isPresent());
        assertEquals(foundTrade.get().getId(), 5);
    }

    @Test
    void shouldNotFindTradeWhenTradeDoesNotExist() {
        int tradeToFindId = 99;
        Optional<Trade> optionalFoundTrade = tradeRepository.findById(tradeToFindId);

        assertTrue(optionalFoundTrade.isEmpty());
    }

    @Test
    void shouldNotFindTradeWhenDeleteTrade() {
        int tradeToDeleteId = 5;
        tradeRepository.deleteById(tradeToDeleteId);
        Optional<Trade> optionalTrade = tradeRepository.findById(tradeToDeleteId);

        assertFalse(optionalTrade.isPresent());
    }
}
