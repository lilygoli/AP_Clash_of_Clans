package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;

import java.io.IOException;

public class ClientInputListener extends Thread{
    public void run() {
        while(true) {
            try {
                Object command = AttackMapUI.clientObjectInput.readObject();
                if (command.getClass().getSimpleName().equals("String")) {
                    String stringCommand = (String) command;
                    if (stringCommand.equals("giveVillage")) {
                        //System.out.println("our game:" + MapUI.getController().getGame());
                        AttackMapUI.clientObjectOutput.writeObject(MapUI.getController().getGame());
                    }
                    else {
                        SideBarUI.clientsComboBox.getItems().clear();
                        SideBarUI.clientsComboBox.getItems().addAll(stringCommand.split("\n"));
                    }
                }
                else if (command.getClass().equals("Game")) {
                    System.out.println(command);
                    MapUI.getController().getGame().setAttackedVillage((Game)command);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
