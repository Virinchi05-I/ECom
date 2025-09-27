package com.ECom.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ECom.ecommerce.entities.RefreshToken;
import com.ECom.ecommerce.entities.User;

import java.util.Optional;


@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    // find all tokens of a user
    Optional<RefreshToken> findByUser(User user);

    // delete all tokens of a user (useful on logout all devices)
    void deleteByUser(User user);
}
