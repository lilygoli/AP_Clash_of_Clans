package com.company.Multiplayer;

import java.io.IOException;

public class ServerInputListener extends Thread{
    private ClientOnServer client;

    public ClientOnServer getClient() {
        return client;
    }

    public void setClient(ClientOnServer client) {
        this.client = client;
    }

    public ServerInputListener(ClientOnServer client) {
        this.client = client;
    }

    public void run() {
        try {
            Object command = client.getInput().readObject();
            if (command.getClass().getSimpleName().equals("String")) {
                String stringCommand = (String) command;
                if (stringCommand.equals("give clients")) {
                    client.getOutput().writeObject(Server.clients);
                }
                else {
                    for (ClientOnServer clientOnServer : Server.clients) {
                        if (clientOnServer.getName().equals(stringCommand)) {

                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
