package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.Models.Village;

import java.io.IOException;

public class ServerInputListener extends Thread{
    private ClientOnServer client;
    private StringBuilder chats = new StringBuilder();

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
        while(true) {
            try {
                Object command = client.getInput().readObject();
                if (command.getClass().getSimpleName().equals("String")) {
                    String stringCommand = (String) command;
                    if (stringCommand.equals("giveClients")) {
                        StringBuilder clients = new StringBuilder();
                        for (ClientOnServer clientOnServer : Server.clients) {
                            clients.append(clientOnServer.getName() + "\n");
                        }
                        client.getOutput().writeObject(clients.toString());
                    } else if (stringCommand.charAt(0) == '&') {
                        chats.append(stringCommand.substring(1 , stringCommand.length()) + "\n");
                        for (ClientOnServer clientOnServer : Server.clients) {
                            clientOnServer.getOutput().writeObject(chats.toString());
                        }
                    }
                    else{
                        for (ClientOnServer clientOnServer : Server.clients) {
                            if (clientOnServer.getName().equals(stringCommand)) {
                                clientOnServer.getOutput().writeObject("giveVillage");
                                Game game = (Game) clientOnServer.getInput().readObject();
                                client.getOutput().writeObject(game);
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }
    }
}
