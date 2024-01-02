package com.kyunashi.gameshow.model;

import lombok.AllArgsConstructor;

public class Player {

    public Player(String playerId, String name, String color) {
        this.playerId = playerId;
        this.name = name;
        this.color = color;
    }

    public Player() {
    }

    private String playerId;
    private String name;

    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
