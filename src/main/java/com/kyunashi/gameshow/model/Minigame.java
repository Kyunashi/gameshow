package com.kyunashi.gameshow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name="minigames")
//@AllArgsConstructor
//@NoArgsConstructor
public class Minigame {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="minigame_id")
//    @Id
    private int minigameId;

    private String description;

    private String gameName;


    // GETTERS SETTERS
    public int getMinigameId() {
        return minigameId;
    }

    public void setMinigameId(int minigameId) {
        this.minigameId = minigameId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
