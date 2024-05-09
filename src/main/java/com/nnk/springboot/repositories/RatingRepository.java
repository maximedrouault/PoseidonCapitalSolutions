package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RatingRepository is a JPA repository interface for Rating.
 * It provides methods to perform CRUD operations on the Rating entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}