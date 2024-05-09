package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BidListRepository is a JPA repository interface for BidList.
 * It provides methods to perform CRUD operations on the BidList entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}