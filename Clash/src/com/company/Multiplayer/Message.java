package com.company.Multiplayer;

import com.company.Models.Game;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Message implements Serializable {
    private Game game;
    private String name;
    private InetAddress ip;
    public Message(Game game,String name){
        this.game=game;
        this.name=name;
        try{
            InetAddress IP=InetAddress.getLocalHost();
            ip = IP;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public InetAddress getIp() {
        return ip;
    }
}
