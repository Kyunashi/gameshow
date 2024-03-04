package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.socket.ReceiveMessage;
import com.kyunashi.gameshow.socket.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@AllArgsConstructor
public class MessageController {

    @MessageMapping("/app")
    public ResponseMessage handle(ReceiveMessage receiveMessage)  {
        return new ResponseMessage("Hello, " + HtmlUtils.htmlEscape(receiveMessage.getName()) + "!");
    }


}
