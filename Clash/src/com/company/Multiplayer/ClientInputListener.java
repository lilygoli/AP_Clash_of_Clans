package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientInputListener extends Thread{
    public void run() {
        while(true) {
            try {
                Object command = AttackMapUI.clientObjectInput.readObject();
                if (command.getClass().getSimpleName().equals("String")) {
                    String stringCommand = (String) command;
                    if (stringCommand.equals("giveVillage")) {

                        AttackMapUI.clientObjectOutput.writeObject(MapUI.getController().getGame());
                        AttackMapUI.clientObjectOutput.flush();
                    }
                    else {
                        SideBarUI.availableVillagesToAttack.clear();
                        if(stringCommand.contains(AttackMapUI.clientName)){
                            stringCommand=stringCommand.replace(AttackMapUI.clientName,"");
                        }
                        SideBarUI.availableVillagesToAttack = new ArrayList<String>(Arrays.asList(stringCommand.split("\n")));
                        //SideBarUI.clientsComboBox.getItems().addAll(stringCommand.split("\n"));
                    }
                }
                else if (command.getClass().getSimpleName().equals("Game")) {
                    System.out.println(command);
                    MapUI.getController().getGame().setAttackedVillage((Game)command);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
