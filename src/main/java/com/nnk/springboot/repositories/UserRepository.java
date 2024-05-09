package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserRepository is a JPA repository interface for User.
 * It provides methods to perform CRUD operations on the User entity.
 * It extends JpaRepository which provides JPA related methods out of the box.
 * It also extends JpaSpecificationExecutor which adds functionality for pagination and sorting.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Find a user by their username.
     * @param username the username of the user to find.
     * @return the User object if found, null otherwise.
     */
    User findByUsername(String username);
}