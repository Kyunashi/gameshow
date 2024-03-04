package com.kyunashi.gameshow.socket;

public class ReceiveMessage {

    private String name;

    public ReceiveMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
