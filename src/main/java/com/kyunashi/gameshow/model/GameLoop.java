package com.kyunashi.gameshow.model;



import com.kyunashi.gameshow.dto.PlayerDto;
import com.kyunashi.gameshow.dto.RoomUpdateDto;
import com.kyunashi.gameshow.event.RoomUpdateEvent;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


@CommonsLog
public class GameLoop implements Runnable {


    private ApplicationEventPublisher eventPublisher;
    private GameStatus status = GameStatus.STOPPED;
    private int count;
    private GameRoom room;

    public GameLoop(GameRoom room, ApplicationEventPublisher eventPublisher) {
        this.room = room;
        count = 0;
        this.eventPublisher = eventPublisher;
    }

    public void start() {
        this.status = GameStatus.RUNNING;
        run();
    }
    @Override
    public void run() {
        while(status == GameStatus.RUNNING) {
            try {
                update();
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        count++;
        log.info("THREAD COUNT : " + count);
        List<GamePlayer> players = new ArrayList<>(room.getPlayers().values());
        RoomUpdateEvent roomUpdateEvent = new RoomUpdateEvent(this, room.getRoomId(), players, count);
        eventPublisher.publishEvent(roomUpdateEvent);
        // send messsages
        // process events?
        // process inputs ( is that even separate?)
    }

    public void stop() {
        this.status = GameStatus.STOPPED;
    }
}
