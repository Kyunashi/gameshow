package com.kyunashi.gameshow.socket;

import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdate {

    public String content;

    private ArrayList<Player> players;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
