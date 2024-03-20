package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.CreateRoomMessage;
import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.dto.RoomDto;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import com.kyunashi.gameshow.dto.JoinRoomMessage;
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
    public String createRoom(CreateRoomMessage createRoomMessage) {
        String roomId = RandomStringUtils.randomAlphanumeric(10);

        while(roomRepository.existsByRoomId(roomId)) {
            roomId = RandomStringUtils.randomAlphanumeric(10);
        }
        Player player = new Player();
        player.setName(createRoomMessage.getName());
        player.setColor(createRoomMessage.getColor());
        playerRepository.save(player);
        Room room = new Room();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        log.info("OWNER ID: " + player.getPlayerId());
        log.info("PLAYERS: ");
        for (Player player1 : players) {
            log.info(player1.getName());
        }
//        room.setOwner(player);
        room.setRoomId(roomId);
        room.setPlayers(players);
        roomRepository.save(room);
        return roomId;

    }

    public int joinRoom(JoinRoomMessage joinRoomMessage) {

        Room room = roomRepository.findByRoomId(joinRoomMessage.getRoomId()).get();

        Player player = new Player();
        player.setColor(joinRoomMessage.getColor());
        player.setName(joinRoomMessage.getName());
        room.addPlayer(player);
        roomRepository.save(room);
        return room.getPlayers().size() - 1;
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
