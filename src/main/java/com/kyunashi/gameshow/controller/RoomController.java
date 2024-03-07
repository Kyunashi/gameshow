package com.kyunashi.gameshow.controller;


import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private RoomService roomService;

    //TODO how do i associate a player with a request? (playerid, but server would need to assign that, else everyone would hav ethe same, or itd be random?
    // maybe leave this for frontend? idk
    // ALSO duplicate names? allowed or not
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody PlayerDto playerDto, HttpServletResponse res) {
        Player owner = new Player(1, playerDto.getName(), playerDto.getColor());
        String roomId = roomService.createRoom(owner);// TODO return room / roomresponse
        return ResponseEntity.status(HttpStatus.OK).body(roomId);

    }

    // TODO DONT NEED THIS ANYMORE
    @GetMapping("/join/{roomId}")
    public ResponseEntity<String> joinRoom(@PathVariable String roomId, @RequestBody PlayerDto playerDto, HttpServletRequest req, HttpServletResponse res){
        roomService.joinRoom(playerDto, roomId);
        return ResponseEntity.status(HttpStatus.OK ).body("Player " + playerDto.getName() + "joined room " + roomId);
    }


    @PostMapping("/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        boolean deleted = roomService.deleteRoom(roomId);
        if(!deleted) {
            return new ResponseEntity<>("No room found with id " + roomId, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Room Deleted");
        //TODO ROOM DELETION, check if room owner?? (or frontendtask?)
    }
    @GetMapping("/{roomId}")
    public void getRoom() {
        // do i even need this?
    }


}
