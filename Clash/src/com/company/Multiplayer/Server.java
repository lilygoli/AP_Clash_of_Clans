package com.company.Multiplayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ServerSocket serverSocket;
    public static ArrayList<ClientOnServer> clients = new ArrayList<>();

    public ArrayList<ClientOnServer> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ClientOnServer> clients) {
        this.clients = clients;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        serverInitialization();
        Socket socket = new Socket();

    }

    private void serverInitialization() {
        try {
            serverSocket = new ServerSocket(12345);
            Thread clientAcceptor = new ClientAcceptor(this);
            clientAcceptor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
