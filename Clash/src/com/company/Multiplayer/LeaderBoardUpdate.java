package com.company.Multiplayer;

import com.company.UIs.AttackMapUI;

import java.io.IOException;
import java.io.Serializable;

public class LeaderBoardUpdate extends Thread {
    @Override
    public void run() {
        while(true) {
            Server.leaderBoard = new StringBuilder("*");
//            for (ClientOnServerLeaderBoard clientOnServerLeaderBoard : Server.clientOnServerLeaderBoards) {
//                try {
//                    clientOnServerLeaderBoard.getOutput().writeObject("$");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            for (ClientOnServer client : Server.clients) {
                try {
                    if(!client.getClientSocket().isClosed())
                        client.getOutput().writeObject("$");
                } catch (IOException e) {
                    try {
                        client.getOutput().close();
                        client.getInput().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
