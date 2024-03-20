package com.kyunashi.gameshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "minigames")
public class Minigame {

    @GeneratedValue
    @Id
    private int minigameId;

    private String title;

    private String description;

    public int getMinigameId() {
        return minigameId;
    }

    public void setMinigameId(int minigameId) {
        this.minigameId = minigameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
