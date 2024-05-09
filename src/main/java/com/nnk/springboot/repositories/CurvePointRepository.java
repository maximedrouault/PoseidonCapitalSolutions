package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CurvePointRepository is a JPA repository interface for CurvePoint.
 * It provides methods to perform CRUD operations on the CurvePoint entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}