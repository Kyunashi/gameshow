package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.service.RoomService;
import com.kyunashi.gameshow.dto.CreateRoomMessage;
import com.kyunashi.gameshow.dto.JoinRoomMessage;
import com.kyunashi.gameshow.dto.RoomUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.util.HtmlUtils;

@Controller
@AllArgsConstructor
@CommonsLog
public class MessageController implements ApplicationListener<SessionConnectedEvent> {

    public RoomService roomService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/join")
//    @SendTo("/user/queue/room-updates")
    public void joinRoom(JoinRoomMessage joinRoomMessage)  {
        int playerIndex = roomService.joinRoom(joinRoomMessage);
        RoomUpdate roomUpdate = new RoomUpdate();
        roomUpdate.setPlayerIndex(playerIndex);
        roomUpdate.setRoomId(joinRoomMessage.getRoomId());
        roomUpdate.setPlayers(roomService.getPlayersOfRoom(joinRoomMessage.getRoomId()));
        String path = "/room/" + joinRoomMessage.getRoomId() + "/updates";
        log.info("Sending message to: " + path);
        simpMessagingTemplate.convertAndSend(path , roomUpdate);
    }


    @MessageMapping("/create")
    @SendTo("/user/queue/room-updates")
    public RoomUpdate createRoom(CreateRoomMessage createRoomMessage) {

        String roomId = roomService.createRoom(createRoomMessage);
        RoomUpdate roomUpdate = new RoomUpdate();
        roomUpdate.setPlayerIndex(roomService.getPlayersOfRoom(roomId).size() - 1);
        roomUpdate.setRoomId(roomId);
        roomUpdate.setPlayers(roomService.getPlayersOfRoom(roomId));
        return roomUpdate;
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
