package com.company.Multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientOnServer implements Serializable{
    private String name;
    private transient Socket clientSocket;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private transient ObjectInputStream leaderBoardInput;
    private transient ObjectOutputStream leaderBoardOutput;


    public String getName() {
        return name;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getLeaderBoardInput() {
        return leaderBoardInput;
    }

    public ObjectOutputStream getLeaderBoardOutput() {
        return leaderBoardOutput;
    }

    public ClientOnServer(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        input = new ObjectInputStream(this.clientSocket.getInputStream());
        output = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.name = (String)input.readObject();
       // this.output.writeObject(Server.clients);

        ServerInputListener inputListener = new ServerInputListener(this);
        inputListener.start();
    }
}
