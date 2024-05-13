package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
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
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;


    @Test
    void shouldReturnSavedRatingWhenSaveRating() {
        Rating ratingToSave = Rating.builder().moodysRating("Moodys Rating save test").sandPRating("Sand PRating save test").fitchRating("Fitch Rating save test").orderNumber(10).build();

        Rating savedRating = ratingRepository.save(ratingToSave);

        assertNotNull(savedRating.getId());
        assertEquals(ratingToSave, savedRating);
    }

    @ParameterizedTest
    @MethodSource("provideEntriesForTest")
    void shouldThrowExceptionWhenRatingToSaveIsNotValid(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        Rating ratingToSave = Rating.builder().moodysRating(moodysRating).sandPRating(sandPRating).fitchRating(fitchRating).orderNumber(orderNumber).build();

        assertThrows(ConstraintViolationException.class, () -> ratingRepository.save(ratingToSave));
    }

    static Stream<Arguments> provideEntriesForTest() {
        return Stream.of(
                Arguments.of(null, null, null, null),
                Arguments.of("", "", "", -1),
                Arguments.of(" ", " ", " ", 0),
                Arguments.of("p".repeat(126), "p".repeat(126), "p".repeat(126), 2147483647)
        );
    }

    @Test
    void shouldReturnUpdatedRatingWhenUpdateRating() {
        Rating ratingToUpdate = Rating.builder().moodysRating("Moodys Rating update test").sandPRating("Sand PRating update test").fitchRating("Fitch Rating update test").orderNumber(20).build();

        Rating updatedRating = ratingRepository.save(ratingToUpdate);

        assertNotNull(updatedRating.getId());
        assertEquals(ratingToUpdate, updatedRating);
    }

    @Test
    void shouldCreateNewRatingWhenRatingToUpdateDoesNotExist() {
        Rating ratingToUpdate = Rating.builder().id(9999).moodysRating("Moodys Rating update test").sandPRating("Sand PRating update test").fitchRating("Fitch Rating update test").orderNumber(20).build();

        Rating savedRating = ratingRepository.save(ratingToUpdate);

        assertNotEquals(9999, savedRating.getId());
    }

    @Test
    void shouldReturnFoundRatingsWhenFindRatings() {
        List<Rating> foundRatings = ratingRepository.findAll();

        assertFalse(foundRatings.isEmpty());
    }

    @Test
    void shouldReturnFoundRatingWhenFindRating() {
        int ratingToFindId = 5;
        Optional<Rating> foundRating = ratingRepository.findById(ratingToFindId);

        assertTrue(foundRating.isPresent());
        assertEquals(foundRating.get().getId(), 5);
    }

    @Test
    void shouldNotFindRatingWhenRatingDoesNotExist() {
        int ratingToFindId = 99;
        Optional<Rating> optionalFoundRating = ratingRepository.findById(ratingToFindId);

        assertTrue(optionalFoundRating.isEmpty());
    }

    @Test
    void shouldNotFindRatingWhenDeleteRating() {
        int ratingToDeleteId = 5;
        ratingRepository.deleteById(ratingToDeleteId);
        Optional<Rating> optionalRating = ratingRepository.findById(ratingToDeleteId);

        assertFalse(optionalRating.isPresent());
    }
}
