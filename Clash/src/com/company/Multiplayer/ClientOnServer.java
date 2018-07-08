package com.company.Multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientOnServer {
    private String name;
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientOnServer(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        input = new ObjectInputStream(clientSocket.getInputStream());
        output = new ObjectOutputStream(clientSocket.getOutputStream());
    }
}
