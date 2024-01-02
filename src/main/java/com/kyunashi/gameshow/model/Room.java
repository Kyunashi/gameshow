package com.kyunashi.gameshow.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<Rally> playedRallies;
    private Rally activeRally;
    private String roomId;

    private Player director;

    public Room(String roomId, Player director) {
        this.roomId = roomId;
        playedRallies = new ArrayList<>();
        this.director = director;
    }

    public Rally getActiveRally() {
        return activeRally;
    }

    public void setActiveRally(Rally activeRally) {
        this.activeRally = activeRally;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void addRally(Rally rally) {
        playedRallies.add(rally);
    }


}
