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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ClientOnServer(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        input = new ObjectInputStream(clientSocket.getInputStream());
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        this.name = (String)input.readObject();
        this.output.writeObject(Server.clients);
    }
}
