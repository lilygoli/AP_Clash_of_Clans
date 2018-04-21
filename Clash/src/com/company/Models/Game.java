package com.company.Models;

import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class Game {
    private String playerName;
    private Village village;
    private int time;
    private boolean isUnderAttackOrVillage;
    private Game attackedVillage;
    private static String whereIAm;
    private ArrayList<Soldier> troops;

    public void showResources(){
        village.showSourcesInfo();
    }

    public void showAttackMenu(){
        for (Game game : GameCenter.getGames()) {
            ;
        }
    }
    public void showBuildings() {

    }
}
