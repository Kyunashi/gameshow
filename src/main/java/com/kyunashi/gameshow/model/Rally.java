package com.kyunashi.gameshow.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@AllArgsConstructor
//@Table(name="rallies")
//@NoArgsConstructor
public class Rally {

    private Gamemaster gamemaster;

    private List<Player> players = new ArrayList<>();

    private List<Minigame> minigames = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rally_id")
    private Long rallyId;


    public Gamemaster getGamemaster() {
        return gamemaster;
    }

    public void setGamemaster(Gamemaster gamemaster) {
        this.gamemaster = gamemaster;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Minigame> getMinigames() {
        return minigames;
    }

    public void setMinigames(List<Minigame> minigames) {
        this.minigames = minigames;
    }

    public Long getRallyId() {
        return rallyId;
    }

    public void setRallyId(Long rallyId) {
        this.rallyId = rallyId;
    }
}
