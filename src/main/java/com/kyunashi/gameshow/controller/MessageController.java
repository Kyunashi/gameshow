package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.socket.ReceiveMessage;
import com.kyunashi.gameshow.socket.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.bridge.Message;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
@AllArgsConstructor
@CommonsLog
public class MessageController implements ApplicationListener<SessionConnectedEvent> {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hi")
    @SendTo("/room/greetings")
    public ResponseMessage handle(ReceiveMessage receiveMessage)  {
        return new ResponseMessage("Hello, " + HtmlUtils.htmlEscape(receiveMessage.getName()) + "!");
    }

//    @MessageMapping("/create")
//    public void sendOnCreate(@Payload Message msg, Principal user) {
//        log.info("CREATE CALLED");
//    }
//
//    @MessageMapping("/join")
//    public void sendOnJoin() {
//        log.info("JOIN CALLED");
//    }
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        log.info("User: " + event.getUser() + "\nwrote: " + event.getMessage() + "\nsource: " + event.getSource());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }



}
