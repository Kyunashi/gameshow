package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.event.RoomUpdateEvent;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@AllArgsConstructor
@CommonsLog
public class MessageController  {

    public RoomService roomService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/join")
    @SendTo("/user/queue/room-updates")
    public RoomConfirmDto joinRoom(JoinRoomMessage joinRoomMessage)  {
        RoomConfirmDto roomConfirm = roomService.joinRoom(joinRoomMessage);

        RoomUpdateDto roomUpdate = new RoomUpdateDto();
        roomUpdate.setRoomId(roomConfirm.getRoomId());
        roomUpdate.setPlayers(roomConfirm.getPlayers());

        String path = "/room/" + roomConfirm.getRoomId() + "/updates";
        log.info("Sending message to: " + path);

        // update the requesting user via room-updates, the rest via template to roomid
        simpMessagingTemplate.convertAndSend(path, roomUpdate);
        return roomConfirm;


    }


    @MessageMapping("/create")
    @SendTo("/user/queue/room-updates")
    public RoomConfirmDto createRoom(CreateRoomMessage createRoomMessage, SimpMessageHeaderAccessor accessor) {
        RoomConfirmDto roomConfirmDto = roomService.createRoom(accessor.getSessionId(), createRoomMessage);
        log.info("ROOMCONFIRM in CONTROLLER: " + roomConfirmDto.getRoomId() + roomConfirmDto.getPlayers());
        return roomConfirmDto;
    }

    @EventListener
    public void onSubscribe(SessionSubscribeEvent event) {


    }

    @EventListener
    public void onConnectedEvent(SessionConnectedEvent event) {
        log.info("User: " + event.getUser() + "\nwrote: " + event.getMessage() + "\nsource: " + event.getSource());
    }

    @EventListener
    public void onDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        roomService.disconnectSession(sessionId);
    }

    @EventListener
    public void onRoomUpdate(RoomUpdateEvent event) {
        List<PlayerDto> players = new ArrayList<>();
        event.getPlayers().forEach((player) -> {
            PlayerDto playerDto = new PlayerDto(player.getName(), player.getColor());
            players.add(playerDto);
        });
        RoomUpdateDto roomUpdateDto = new RoomUpdateDto(event.getRoomId(), players);
        String path = "/room/" + event.getRoomId() + "/updates";
        log.info("ROOM UPDATE TO " + path + "---- COUNT: " + event.getCount());
        simpMessagingTemplate.convertAndSend(path, roomUpdateDto);
    }
}
