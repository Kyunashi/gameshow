package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import com.kyunashi.gameshow.repository.PlayerRepository;
import com.kyunashi.gameshow.repository.RoomRepository;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Service
@CommonsLog
@AllArgsConstructor
public class RoomService {


    RoomRepository roomRepository;
    PlayerRepository playerRepository;

    @Transactional
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

    @Transactional
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

    @Transactional
    public boolean deleteRoom(String roomId) {
        if (roomRepository.existsByRoomId(roomId)) {
            roomRepository.deleteByRoomId(roomId);

            return true;
        }
        return false;
    }

    @Transactional
    public void disconnectSession(String sessionId) {

        Optional<Room> room = roomRepository.findRoomBySession(sessionId);
        if(!room.isEmpty()) {
            roomRepository.deleteByRoomId(room.get().getRoomId());
            log.info("Delete ROom with session: " + sessionId);
        }
    }

    public List<PlayerDto> getPlayersOfRoom(String roomId) {
        return roomRepository.findByRoomId(roomId).get().getPlayersAsDto();
    }



    public void addSessionToRoom(String sessionId, String roomId) {
        Room room = roomRepository.findByRoomId(roomId).get();
        if(room.getSession() == null || room.getSession().isEmpty() || room.getSession().isBlank()) {
            room.setSession(sessionId);
            roomRepository.save(room);
            log.info("ADDED SESSION: " + sessionId + " TO ROOM: " + roomId);
        }
    }


}
