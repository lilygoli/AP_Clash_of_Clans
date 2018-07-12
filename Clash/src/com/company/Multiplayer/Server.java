package com.company.Multiplayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ServerSocket leaderBoardServerSocket;
    public static StringBuilder chats = new StringBuilder("@");
    public static StringBuilder leaderBoard = new StringBuilder("*");
    public static ServerSocket serverSocket;
    public static ArrayList<ClientOnServer> clients = new ArrayList<>();
    public static ArrayList<ClientOnServerLeaderBoard> clientOnServerLeaderBoards = new ArrayList<>();
    public static int clientcounter = 0;

    public ArrayList<ClientOnServer> getClients() {
        return clients;
    }

    public ServerSocket getLeaderBoardServerSocket() {
        return leaderBoardServerSocket;
    }

    public static void setClientOnServerLeaderBoards(ArrayList<ClientOnServerLeaderBoard> clientOnServerLeaderBoards) {
        Server.clientOnServerLeaderBoards = clientOnServerLeaderBoards;
    }

    public  ArrayList<ClientOnServerLeaderBoard> getClientOnServerLeaderBoards() {
        return clientOnServerLeaderBoards;
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
            leaderBoardServerSocket = new ServerSocket(12347);
            Thread clientAcceptor = new ClientAcceptor(this);
            Thread clientLeaderBoardAcceptor =  new ClientLeaderBoardAcceptor(this);
            clientAcceptor.start();
            clientLeaderBoardAcceptor.start();
            new LeaderBoardUpdate().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
