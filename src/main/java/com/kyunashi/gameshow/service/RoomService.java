package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.Player;
import com.kyunashi.gameshow.model.Room;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RoomService {


    // TODO DECIDE ON BEAN  / IN MEMORY / COLLECTION --> where do i need the data
    public void createRoom(Player creator) {
        String roomId = RandomStringUtils.randomAlphanumeric(10);
        Room room = new Room(roomId, creator);

    }
}
