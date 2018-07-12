package com.company.Multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientOnServerLeaderBoard {
    private String name;
    private transient Socket clientSocket;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;



    public String getName() {
        return name;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ClientOnServerLeaderBoard(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        input = new ObjectInputStream(this.clientSocket.getInputStream());
        output = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.name = (String)input.readObject();
        // this.output.writeObject(Server.clients);

        //LeaderBoardListenerOnServer listerner = new LeaderBoardListenerOnServer();
        //listerner.start();
    }
}
