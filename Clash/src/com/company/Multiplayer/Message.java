package com.company.Multiplayer;

import com.company.Models.Game;

import java.io.Serializable;

public class Message implements Serializable {
    private Game game;
    private String name;
    public Message(Game game,String name){
        this.game=game;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }
}
