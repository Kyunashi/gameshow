package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.dto.RoomDto;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoomService {


    // TODO DECIDE ON BEAN  / IN MEMORY / COLLECTION --> where do i need the data
    Map<String, Room> rooms = new HashMap();
    public String createRoom(Player owner) {
        String roomId = RandomStringUtils.randomAlphanumeric(10);

        while(rooms.containsKey(roomId)) {
            roomId = RandomStringUtils.randomAlphanumeric(10);
        }
        Room room = new Room(roomId, owner);
        rooms.put(roomId, room);
        RoomDto roomRes = new RoomDto();
        return roomId;

    }

    public void joinRoom(PlayerDto playerDto, String roomId) {

        Room room = rooms.get(roomId);
        Player player = new Player(rooms.size() + 1, playerDto.getName(), playerDto.getColor() );
        room.addPlayer(player);
    }

    public boolean deleteRoom(String roomId) {
        if (rooms.containsKey(roomId)) {
            rooms.remove(roomId);
            return true;
        }
        return false;
    }


}
