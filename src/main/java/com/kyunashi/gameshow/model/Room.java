package com.kyunashi.gameshow.model;

import java.util.ArrayList;
import java.util.List;

public class Room {


    private String roomId;

    private ArrayList<Player> players;

    private Player gamemaster;

    private Player owner;

    private List<Integer> minigames;

    public Room(String roomId, Player owner) {
        this.roomId = roomId;
        this.owner = owner;
        this.gamemaster = owner;
        this.players = new ArrayList<>();
        this.minigames = new ArrayList<>();
    }



    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void addPlayer(Player player) {
        this.players.add(player);

    }


}
