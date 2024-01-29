package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.JoinRequest;
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
        Room room = new Room(roomId, owner);

        if(!rooms.keySet().contains(roomId)) {
            rooms.put(roomId, room);
            return roomId;
        }
        // TODO EDGECASE PREVENTION (repaet if id was ins keyset)
        return "";

    }

    public void joinRoom(JoinRequest joinRequest) {

        Room room = rooms.get(joinRequest.getRoomId());
        Player player = new Player(rooms.size() + 1,joinRequest.getPlayerName(), joinRequest.getColor() );
        room.addPlayer(player);
    }

//    public Room GetRoomById(String id) {
//
//        return rooms.get(id);
//    }
}
