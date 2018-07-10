package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;
import javafx.geometry.Side;

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
                    if (stringCommand.startsWith("giveVillage")) {
                       AttackMapUI.clientObjectOutput.writeObject(new Message(MapUI.getController().getGame(),stringCommand.split("\n")[1]));
                       AttackMapUI.getController().getGame().setUnderAttackOrDefense(true);
                       UDPReceiver udpReceiver = new UDPReceiver();
                       udpReceiver.start();
                        AttackMapUI.clientObjectOutput.flush();
                    }
                    else if (stringCommand.charAt(0) == '@') {
                        SideBarUI.chatsArea.clear();
                        SideBarUI.chatsArea.setText(stringCommand.substring(1, stringCommand.length()));
                    }
                    else {
                        SideBarUI.availableVillagesToAttack.clear();
                        if(stringCommand.contains(AttackMapUI.clientName)){
                            stringCommand = stringCommand.replace(AttackMapUI.clientName+"\n","");
                        }
                        SideBarUI.availableVillagesToAttack = new ArrayList<String>(Arrays.asList(stringCommand.split("\n")));
                        //SideBarUI.clientsComboBox.getItems().addAll(stringCommand.split("\n"));
                    }
                }
                else if (command.getClass().getSimpleName().equals("Message")) {
                    System.out.println(command+"thread?");
                    System.out.println(AttackMapUI.clientName);
                    MapUI.getController().getGame().setAttackedVillage(((Message)command).getGame());
                    AttackMapUI.attackedIP = ((Message) command).getIp();

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
