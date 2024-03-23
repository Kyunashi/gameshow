package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.*;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@CommonsLog
public class MessageController implements ApplicationListener<SessionConnectedEvent> {

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

//
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        log.info("User: " + event.getUser() + "\nwrote: " + event.getMessage() + "\nsource: " + event.getSource());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }



}
