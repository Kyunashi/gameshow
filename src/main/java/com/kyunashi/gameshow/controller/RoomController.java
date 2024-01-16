package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.PlayerRequest;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;

    @PostMapping("/create")
    public void createRoom(@RequestBody PlayerRequest playerRequest) {
        Player player = new Player(playerRequest.getPlayerId(), playerRequest.getName(),playerRequest.getColor());
        roomService.createRoom(player);
        // return Join Link

    }

    @GetMapping("/join")
    public void joinRoom(){

    }



    @GetMapping("/{roomId}")
    public void getRoom() {

    }

}
