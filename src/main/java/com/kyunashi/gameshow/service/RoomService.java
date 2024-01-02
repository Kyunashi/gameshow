package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import org.apache.commons.lang3.RandomStringUtils;

public class RoomService {

    public void createRoom(Player creator) {
        String roomId = RandomStringUtils.randomAlphanumeric(10);
        Room room = new Room(roomId, creator);
    }
}
