package com.kyunashi.gameshow.event;

import com.kyunashi.gameshow.dto.RoomUpdateDto;
import com.kyunashi.gameshow.model.GamePlayer;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class RoomUpdateEvent extends ApplicationEvent {

    private String roomId;
    private List<GamePlayer> players;
    private int count;



    public RoomUpdateEvent(Object source, String roomId, List<GamePlayer> players, int count) {
        super(source);
        this.roomId = roomId;
        this.players = players;
        this.count = count;
    }


    public String getRoomId() {
        return roomId;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public int getCount() {
        return count;
    }
}
