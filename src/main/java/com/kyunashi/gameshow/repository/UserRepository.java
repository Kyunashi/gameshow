package com.kyunashi.gameshow.repository;

import com.kyunashi.gameshow.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to get User data from the users table utilizing JpaRepository which maps the request to actual sql requests that are used to query the database
 * method names should be selfexplanatory
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByUserId(int userId);
}
