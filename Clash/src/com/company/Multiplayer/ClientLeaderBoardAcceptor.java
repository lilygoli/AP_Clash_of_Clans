package com.company.Multiplayer;

import java.net.Socket;

public class ClientLeaderBoardAcceptor extends Thread {
    Server server;

    public ClientLeaderBoardAcceptor(Server server) {
        this.server = server;
    }

    public void run() {
        while(true) {
            try {
                Socket client = server.getLeaderBoardServerSocket().accept();
                server.getClientOnServerLeaderBoards().add(new ClientOnServerLeaderBoard(client));
            } catch (Exception e) {
                this.stop();
                e.printStackTrace();
            }
        }
    }
}
