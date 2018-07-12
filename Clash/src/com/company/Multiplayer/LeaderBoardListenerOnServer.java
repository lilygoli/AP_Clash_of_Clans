package com.company.Multiplayer;

import com.company.UIs.AttackMapUI;

import java.io.IOException;

import static com.company.Multiplayer.Server.leaderBoard;

public class LeaderBoardListenerOnServer extends Thread {
    @Override
    public void run() {
        while(true){
            try {
                String stringCommand = (String)AttackMapUI.leaderBoardInput.readObject();
                if (stringCommand.charAt(0) == '$') {
                    System.out.println("1");
                    //System.out.println("commnad" + stringCommand);
                    leaderBoard.append(leaderBoard + stringCommand.substring(1, stringCommand.length()) + "\n");
                    for (ClientOnServer clientOnServer : Server.clients) {
                        clientOnServer.getOutput().writeObject(leaderBoard.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
