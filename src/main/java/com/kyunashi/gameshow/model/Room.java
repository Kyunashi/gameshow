package com.kyunashi.gameshow.model;

import java.util.ArrayList;
import java.util.List;

public class Room {


    private String roomId;

    private List<Player> players;

    private Player gamemaster;

    private Player owner;

    private List<Integer> minigames;

    public Room(String roomId, Player director) {
        this.roomId = roomId;
        this.owner = director;
        this.players = new ArrayList<>();
        this.minigames = new ArrayList<>();
    }



    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }


}
