package com.kyunashi.gameshow.model;

import com.kyunashi.gameshow.data.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRoom {

    private String roomId;

    private String ownerSessionId;

    private Map<String, GamePlayer> players;

//    private List<Minigame> minigames;

    public GameRoom() {
        players = new HashMap<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getOwner() {
        return ownerSessionId;
    }

    public void setOwner(String ownerSessionId) {
        this.ownerSessionId = ownerSessionId;
    }

    public Map<String, GamePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, GamePlayer> players) {
        this.players = players;
    }

    public void addPlayer(String sessionId, GamePlayer player) {
        this.players.put(sessionId,player);
    }
}
