package com.company.Multiplayer;

import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;

import java.io.IOException;

public class LeaderBoardListenerOnClient extends Thread{
    public void run() {
        while(true) {
            Object command = null;
            try {
                command = AttackMapUI.leaderBoardInput.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            String stringCommand = (String) command;

            if (stringCommand.startsWith("$")){
                try {
                    AttackMapUI.clientObjectOutput.writeObject("$Name : " + AttackMapUI.clientName + " GoldGained : " + Integer.toString(SideBarUI.allGainedGoldsResources + MapUI.getController().getGame().getVillage().getGainedResource().getGold()) + " -- ElixirGained : " + Integer.toString(SideBarUI.allGainedElixirResources + MapUI.getController().getGame().getVillage().getGainedResource().getElixir()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (stringCommand.startsWith("*")) {
                System.out.println("stringcommand" + stringCommand);
                SideBarUI.leaderBoard.setText(stringCommand.substring(1 , stringCommand.length()));
            }

        }
    }
}
