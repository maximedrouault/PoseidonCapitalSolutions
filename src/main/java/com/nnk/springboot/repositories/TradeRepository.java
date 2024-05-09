package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TradeRepository is a JPA repository interface for Trade.
 * It provides methods to perform CRUD operations on the Trade entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
