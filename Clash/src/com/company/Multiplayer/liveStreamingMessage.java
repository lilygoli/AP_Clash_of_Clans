package com.company.Multiplayer;

import com.company.Models.Soldiers.Soldier;
import com.company.UIs.MapUI;

import java.util.ArrayList;

public class liveStreamingMessage {
    ArrayList<Soldier> troops;
    ArrayList<Integer> healths;
    public liveStreamingMessage(){
        troops= MapUI.getController().getGame().getTroops();
        for (int i = 0; i <30 ; i++) {
            for (int j = 0; j <30 ; j++) {
                healths.add(MapUI.getController().getGame().getVillage().getMap()[i][j].getStrength());

            }
        }
    }
}
