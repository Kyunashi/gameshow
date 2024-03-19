package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.dto.RoomDto;
import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import com.kyunashi.gameshow.socket.JoinRoomMessage;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@CommonsLog
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

    public void joinRoom(JoinRoomMessage joinRoomMessage) {

        Room room = rooms.get(joinRoomMessage.getRoomId());
        Player player = new Player(joinRoomMessage.getName(), joinRoomMessage.getColor() );
        room.addPlayer(player);
    }

    public boolean deleteRoom(String roomId) {
        if (rooms.containsKey(roomId)) {
            rooms.remove(roomId);
            return true;
        }
        return false;
    }


    public ArrayList<Player> getPlayersOfRoom(String roomId) {
        return rooms.get(roomId).getPlayers();
    }
}
