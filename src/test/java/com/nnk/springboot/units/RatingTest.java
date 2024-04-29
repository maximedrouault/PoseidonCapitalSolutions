package com.nnk.springboot.units;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
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
public class RatingTest {

	@Autowired
	private RatingRepository ratingRepository;


	@Test
	void shouldReturnSavedRatingWhenSaveRating() {
		Rating ratingToSave = Rating.builder().moodysRating("Moodys Rating").sandPRating("Sand PRating").fitchRating("Fitch Rating").orderNumber(10).build();

		Rating savedRating = ratingRepository.save(ratingToSave);

		assertNotNull(savedRating.getId());
        assertEquals(10, savedRating.getOrderNumber());
	}

	@Test
	void shouldReturnUpdatedRatingWhenUpdateRating() {
		Rating ratingToUpdate = ratingRepository.findById(5).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
		ratingToUpdate.setOrderNumber(20);

		Rating updatedRating = ratingRepository.save(ratingToUpdate);

        assertEquals(20, updatedRating.getOrderNumber());
	}

	@Test
	void shouldReturnFoundRatingsWhenFindAllRating() {
		List<Rating> expectedRating = List.of(Rating.builder().id(5).moodysRating("Moodys Rating").sandPRating("Sand PRating").fitchRating("Fitch Rating").orderNumber(5).build());

		List<Rating> foundRating = ratingRepository.findAll();

        assertFalse(foundRating.isEmpty());
		assertEquals(foundRating, expectedRating);
	}

	@Test
	void shouldNotFindRatingWhenDeleteRating() {
		ratingRepository.deleteById(5);
		Optional<Rating> foundRating = ratingRepository.findById(5);

		assertTrue(foundRating.isEmpty());
	}
}
