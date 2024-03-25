package com.kyunashi.gameshow.repository;

import com.kyunashi.gameshow.data.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByRoomId (String roomId);
    Boolean existsByRoomId(String roomId);

    Boolean existsBySession(String sessionId);

    default void deleteByRoomId(String roomId) {

    }
}
