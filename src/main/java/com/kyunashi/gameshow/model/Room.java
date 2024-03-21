package com.kyunashi.gameshow.model;

import com.kyunashi.gameshow.dto.PlayerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String roomId;

    @OneToOne
    private Player owner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "player_ids")
    private List<Player> players;

    @OneToMany(mappedBy = "minigameId")
    private List<Minigame> minigames;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public void addPlayer(Player player) {
        this.players.add(player);

    }

    public void addMinigame(Minigame minigame)  {
        this.minigames.add(minigame);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setMinigames(List<Minigame> minigames) {
        this.minigames = minigames;
    }

    public List<PlayerDto> getPlayersAsDto() {
        ArrayList<PlayerDto> playersDto = new ArrayList<>();
        this.players.forEach((player) -> {playersDto.add(new PlayerDto(player.getName(), player.getColor()));});
        return playersDto;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Minigame> getMinigames() {
        return minigames;
    }
}
