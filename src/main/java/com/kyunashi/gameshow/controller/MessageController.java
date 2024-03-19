package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.service.RoomService;
import com.kyunashi.gameshow.socket.CreateRoomMessage;
import com.kyunashi.gameshow.socket.JoinRoomMessage;
import com.kyunashi.gameshow.socket.RoomUpdate;
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
    @MessageMapping("/join")
    @SendTo("/user/queue/room-updates")
    public RoomUpdate joinRoom(JoinRoomMessage joinRoomMessage)  {
        roomService.joinRoom(joinRoomMessage);
        RoomUpdate roomUpdate = new RoomUpdate();
        roomUpdate.setContent("Hello " + HtmlUtils.htmlEscape(joinRoomMessage.getName())
                + ", you joined room: " + joinRoomMessage.getRoomId()
                + " as color " + joinRoomMessage.getColor());
        roomUpdate.setPlayers(roomService.getPlayersOfRoom(joinRoomMessage.getRoomId()));
        return roomUpdate;
    }

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/create")
    @SendTo("/user/queue/room-updates")
    public RoomUpdate createRoom(CreateRoomMessage createRoomMessage) {
        String roomId = roomService.createRoom(new Player(createRoomMessage.getName(), createRoomMessage.getColor()));
        RoomUpdate roomUpdate = new RoomUpdate();
        roomUpdate.setContent("Hello " + HtmlUtils.htmlEscape(createRoomMessage.getName())
                + ", you create room: " + roomId
                + " as color " + createRoomMessage.getColor());
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
