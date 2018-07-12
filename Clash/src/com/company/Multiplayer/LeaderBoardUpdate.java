package com.company.Multiplayer;

import java.io.IOException;
import java.io.Serializable;

public class LeaderBoardUpdate extends Thread {
    @Override
    public void run() {
        while(true) {
            Server.leaderBoard = new StringBuilder("*");
            for (ClientOnServer client : Server.clients) {
                try {
                    client.getLeaderBoardOutput().writeObject("$");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
