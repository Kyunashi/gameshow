package com.kyunashi.gameshow.model;

import lombok.AllArgsConstructor;

public class Player {

    public Player(int playerNumber, String name, String color) {
        this.playerNumber = playerNumber;
        this.name = name;
        this.color = color;
    }

    public Player() {
    }

    private int playerNumber;
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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
