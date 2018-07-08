package com.company.Multiplayer;

import java.io.IOException;
import java.net.Socket;

public class ClientAcceptor extends Thread{
    Server server;

    public ClientAcceptor(Server server) {
        this.server = server;
    }

    public void run() {
        while(true) {
            try {
                Socket client = server.getServerSocket().accept();
                server.getClients().add(new ClientOnServer(client));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
