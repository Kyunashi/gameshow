package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@CommonsLog
public class MessageController  {

    public RoomService roomService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/join")
    @SendTo("/user/queue/room-updates")
    public RoomConfirm joinRoom(JoinRoomMessage joinRoomMessage)  {
        RoomConfirm roomConfirm = roomService.joinRoom(joinRoomMessage);

        RoomUpdate roomUpdate = new RoomUpdate();
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
    public RoomConfirm createRoom(CreateRoomMessage createRoomMessage) {

        RoomConfirm roomConfirm = roomService.createRoom(createRoomMessage);
        return roomConfirm;
    }

    @EventListener
    public void onSubscribeToRoomId(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        String destination = accessor.getDestination();
        if (destination.startsWith("/room") && destination.endsWith("/updates")) {
            String roomId = destination.substring(6);
            roomId = roomId.substring(0, roomId.length() - 8);
            roomService.addSessionToRoom(sessionId, roomId);
            log.info("STARTSWITH  / ENDSWITH ROOM ID : " + roomId);
        }

        String simpDestination = accessor.getFirstNativeHeader("simpDestination");
        log.info("sub session: " + sessionId + " --- destination: " + destination + " --- simp dest: " + simpDestination);

    }

    @EventListener
    public void onConnectEvent(SessionConnectedEvent event) {
        log.info("User: " + event.getUser() + "\nwrote: " + event.getMessage() + "\nsource: " + event.getSource());
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        roomService.disconnectSession(sessionId);
    }
}
