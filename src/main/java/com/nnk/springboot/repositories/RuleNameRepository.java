package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RuleNameRepository is a JPA repository interface for RuleName.
 * It provides methods to perform CRUD operations on the RuleName entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
