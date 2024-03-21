package com.kyunashi.gameshow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="players")
public class Player {


    @GeneratedValue
    @Id
    private int playerId;

    private String name;

    private String color;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Room room;
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

    public int getPlayerId() {
        return playerId;
    }

//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
