package com.kyunashi.gameshow.repository;

import com.kyunashi.gameshow.model.Room;
import com.kyunashi.gameshow.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByRoomId (String roomId);
    Boolean existsByRoomId(String roomId);

}
