package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
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
public class BidListRepositoryTest {

    @Autowired
    private BidListRepository bidListRepository;


    @Test
    void shouldReturnSavedBidListWhenSaveBidList() {
        BidList bidListToSave = BidList.builder().account("Account save test").type("Type save test").bidQuantity(10d).build();

        BidList savedBidList = bidListRepository.save(bidListToSave);

        assertNotNull(savedBidList.getId());
        assertEquals(bidListToSave, savedBidList);
    }

    @ParameterizedTest
    @CsvSource({
            ",,",
            "'', '', -1.0",
            "' ', ' ', 0.0",
            "'...............................', '...............................', 1.7976931348623157E309"
    })
    void shouldThrowExceptionWhenBidListToSaveIsNotValid(String account, String type, Double bidQuantity) {
        BidList bidListToSave = BidList.builder().account(account).type(type).bidQuantity(bidQuantity).build();

        assertThrows(ConstraintViolationException.class, () -> bidListRepository.save(bidListToSave));
    }

    @Test
    void shouldReturnUpdatedBidListWhenUpdateBidList() {
        BidList bidListToUpdate = BidList.builder().id(5).account("Account update test").type("Type update test").bidQuantity(10d).build();

        BidList updatedBidList = bidListRepository.save(bidListToUpdate);

        assertEquals(updatedBidList.getId(), 5);
        assertEquals(bidListToUpdate, updatedBidList);
    }

    @Test
    void shouldCreateNewBidListWhenBidListToUpdateDoesNotExist() {
        BidList bidListToUpdate = BidList.builder().id(9999).account("Account update test").type("Type update test").bidQuantity(10d).build();

        BidList savedBidList = bidListRepository.save(bidListToUpdate);

        assertNotEquals(9999, savedBidList.getId());
    }

    @Test
    void shouldReturnFoundBidListsWhenFindBidLists() {
        List<BidList> foundBidLists = bidListRepository.findAll();

        assertFalse(foundBidLists.isEmpty());
    }

    @Test
    void shouldReturnFoundBidListWhenFindBidList() {
        int bidListToFindId = 5;
        Optional<BidList> foundBidList = bidListRepository.findById(bidListToFindId);

        assertTrue(foundBidList.isPresent());
        assertEquals(foundBidList.get().getId(), 5);
    }

    @Test
    void shouldNotFindBidListWhenBidListDoesNotExist() {
        int bidListToFindId = 99;
        Optional<BidList> optionalFoundBidList = bidListRepository.findById(bidListToFindId);

        assertTrue(optionalFoundBidList.isEmpty());
    }

    @Test
    void shouldNotFindBidListWhenDeleteBidList() {
        int bidListToDeleteId = 5;
        bidListRepository.deleteById(bidListToDeleteId);
        Optional<BidList> bidList = bidListRepository.findById(bidListToDeleteId);

        assertFalse(bidList.isPresent());
    }
}
