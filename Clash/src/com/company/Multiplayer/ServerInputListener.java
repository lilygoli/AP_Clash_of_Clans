package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.Models.Village;

import java.io.IOException;

import static com.company.Multiplayer.Server.leaderBoard;

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
        while(true) {
            try {
                StringBuilder chats = Server.chats;
                StringBuilder leaderBoard = Server.leaderBoard;
                Object command = client.getInput().readObject();
                if (command.getClass().getSimpleName().equals("String")) {
                    String stringCommand = (String) command;
                    if (stringCommand.equals("giveClients")) {
                        StringBuilder clients = new StringBuilder();
                        for (ClientOnServer clientOnServer : Server.clients) {
                            clients.append(clientOnServer.getName() + "\n");
                        }
                        client.getOutput().writeObject(clients.toString());
                    }
                    else if (stringCommand.charAt(0) == '&') {
                        chats.append(stringCommand.substring(1 , stringCommand.length()) + "\n");
                        for (ClientOnServer clientOnServer : Server.clients) {
                            clientOnServer.getOutput().writeObject(chats.toString());
                        }
                    }else if (stringCommand.charAt(0) == '$') {
                        System.out.println("1");
                        //System.out.println("commnad" + stringCommand);
                        leaderBoard.append(stringCommand.substring(1, stringCommand.length()) + "\n");
                        for (ClientOnServer clientOnServer : Server.clients) {
                            clientOnServer.getOutput().writeObject(leaderBoard.toString());
                        }
                    }
                    else{
                        for (ClientOnServer clientOnServer : Server.clients) {
                            if (clientOnServer.getName().equals(stringCommand)) {
                                clientOnServer.getOutput().writeObject("giveVillage\n"+client.getName());
                                //   Game game = (Game) clientOnServer.getInput().readObject();
//                                client.getOutput().writeObject(game);
                            }
                        }
                    }
                }else if(command.getClass().getSimpleName().equals("Message")) {
                    for (ClientOnServer clientOnServer : Server.clients) {
                        if (clientOnServer.getName().equals(((Message)command).getName())) {
                            clientOnServer.getOutput().writeObject(command);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println(this);
                break;
            }

        }
    }
}
