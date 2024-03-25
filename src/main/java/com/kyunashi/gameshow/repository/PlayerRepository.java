package com.kyunashi.gameshow.repository;

import com.kyunashi.gameshow.data.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findPlayerByPlayerId(int playerId);
}
