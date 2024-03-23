package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import com.kyunashi.gameshow.repository.PlayerRepository;
import com.kyunashi.gameshow.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@CommonsLog
@AllArgsConstructor
public class RoomService {


    RoomRepository roomRepository;
    PlayerRepository playerRepository;
    public RoomConfirm createRoom(CreateRoomMessage createRoomMessage) {
        String roomId = RandomStringUtils.randomAlphanumeric(10);

        while(roomRepository.existsByRoomId(roomId)) {
            roomId = RandomStringUtils.randomAlphanumeric(10);
        }
        //player
        Player player = new Player();
        player.setName(createRoomMessage.getName());
        player.setColor(createRoomMessage.getColor());
        // room
        Room room = new Room();
        room.setOwner(player);
        room.setRoomId(roomId);
        room.setPlayers(Arrays.asList(player));
//        player.setRoom(room);
        roomRepository.save(room);
        List<PlayerDto> players = getPlayersOfRoom(roomId);
        RoomConfirm roomConfirm = new RoomConfirm(players.size() - 1, roomId, players);
        return roomConfirm;

    }

    public RoomConfirm  joinRoom(JoinRoomMessage joinRoomMessage) {

        Room room = roomRepository.findByRoomId(joinRoomMessage.getRoomId()).get();

        Player player = new Player();
        player.setColor(joinRoomMessage.getColor());
        player.setName(joinRoomMessage.getName());
        room.addPlayer(player);
        roomRepository.save(room);
        List<PlayerDto> players = getPlayersOfRoom(room.getRoomId());
        RoomConfirm roomConfirm = new RoomConfirm(room.getPlayers().size() - 1, room.getRoomId(),players);
        return roomConfirm;
    }

//    public boolean deleteRoom(String roomId) {
//        if (rooms.containsKey(roomId)) {
//            rooms.remove(roomId);
//            return true;
//        }
//        return false;
//    }


    public List<PlayerDto> getPlayersOfRoom(String roomId) {
        return roomRepository.findByRoomId(roomId).get().getPlayersAsDto();
    }
}
