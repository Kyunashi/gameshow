package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.data.Player;
import com.kyunashi.gameshow.data.Room;
import com.kyunashi.gameshow.model.GameLoop;
import com.kyunashi.gameshow.model.GamePlayer;
import com.kyunashi.gameshow.model.GameRoom;
import com.kyunashi.gameshow.repository.PlayerRepository;
import com.kyunashi.gameshow.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@CommonsLog
@AllArgsConstructor
public class RoomService {


    RoomRepository roomRepository;
    PlayerRepository playerRepository;

    private ApplicationEventPublisher eventPublisher;

    public RoomConfirmDto createRoom(String sessionId, CreateRoomMessage createRoomMessage) {
        // make creator player
        GamePlayer creator = new GamePlayer();
        creator.setColor(createRoomMessage.getColor());
        creator.setName(createRoomMessage.getName());
        creator.setConnected(true);
        // make a room with the player and session associated
        String roomId = RandomStringUtils.randomAlphanumeric(10);
        GameRoom room = new GameRoom();
        room.setRoomId(roomId);
        room.setOwner(sessionId);
        room.addPlayer(sessionId, creator);

        // create new gameloop
        Thread gameThread = new Thread(() -> {
            GameLoop game = new GameLoop(room, eventPublisher);
            game.start();
        });
        gameThread.start();

        PlayerDto playerDto = new PlayerDto(creator.getName(), creator.getColor());
        RoomConfirmDto roomConfirm = new RoomConfirmDto(0 , roomId, Arrays.asList(playerDto));
        log.info("ROOMCONFIRM in service: " + roomConfirm.getRoomId() + roomConfirm.getPlayers());
        return roomConfirm;

    }


    @Transactional
    public RoomConfirmDto joinRoom(JoinRoomMessage joinRoomMessage) {

        Room room = roomRepository.findByRoomId(joinRoomMessage.getRoomId()).get();

        Player player = new Player();
        player.setColor(joinRoomMessage.getPlayer().getColor());
        player.setName(joinRoomMessage.getPlayer().getName());
        room.addPlayer(player);
        roomRepository.save(room);
        List<PlayerDto> players = getPlayersOfRoom(room.getRoomId());
        RoomConfirmDto roomConfirm = new RoomConfirmDto(room.getPlayers().size() - 1, room.getRoomId(),players);
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


    }

    public List<PlayerDto> getPlayersOfRoom(String roomId) {
        return roomRepository.findByRoomId(roomId).get().getPlayersAsDto();
    }




}
