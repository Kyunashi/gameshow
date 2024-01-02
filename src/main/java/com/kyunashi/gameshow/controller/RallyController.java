package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.PlayerRequest;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.service.RallyService;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/rally")
public class RallyController {

    private RallyService rallyService;
    private RoomService roomService;

    @PostMapping("/create")
    public void createRoom(@RequestBody PlayerRequest playerRequest) {
        Player player = new Player(playerRequest.getPlayerId(), playerRequest.getName(),playerRequest.getColor());
        roomService.createRoom(player);
        // return Join Link

    }

    @GetMapping("/start")
    public void startRally(){

    }

    @GetMapping("/join")
    public void joinRoom(){

    }
}
