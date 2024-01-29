package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.JoinRequest;
import com.kyunashi.gameshow.dto.PlayerRequest;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    //TODO how do i associate a player with a request? (playerid, but server would need to assign that, else everyone would hav ethe same, or itd be random?
    // maybe leave this for frontend? idk
    // ALSO duplicate names? allowed or not
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody PlayerRequest playerRequest) {
        Player owner = new Player(1, playerRequest.getName(),playerRequest.getColor());
        String roomId = roomService.createRoom(owner);
        return new ResponseEntity<>(roomId, HttpStatus.OK);

    }

    @GetMapping("/join")
    public ResponseEntity<String> joinRoom(@RequestBody JoinRequest joinRequest){

        roomService.joinRoom(joinRequest);
        return new ResponseEntity<>("Player " +joinRequest.getPlayerName() + "joined room " + joinRequest.getRoomId(), HttpStatus.OK);
    }



    @GetMapping("/{roomId}")
    public void getRoom() {

    }

}
