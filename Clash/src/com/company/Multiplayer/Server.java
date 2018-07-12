package com.company.Multiplayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static StringBuilder chats = new StringBuilder("@");
    public static StringBuilder leaderBoard = new StringBuilder("*");
    public static ServerSocket serverSocket;
    public static ArrayList<ClientOnServer> clients = new ArrayList<>();

    public ArrayList<ClientOnServer> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ClientOnServer> clients) {
        Server.clients = clients;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        Server.serverSocket = serverSocket;
    }

    public void start() {
        serverInitialization();
    }

    private void serverInitialization() {
        try {
            serverSocket = new ServerSocket(12345);
            Thread clientAcceptor = new ClientAcceptor(this);
            clientAcceptor.start();
            new LeaderBoardUpdate().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
