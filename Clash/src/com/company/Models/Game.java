package com.company.Models;

import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;

public class Game {
    private static int elexirGained;
    private static int goldGained;
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

    public String showAttackMenu(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("1.Load Map\n");
        int cnt = 2;
        for (Game game : GameCenter.getGames()) {
            finalString.append(cnt + "." + game.playerName + "\n");
            cnt++;
        }
        finalString.append(cnt + ".Back\n");
        return finalString.toString();
    }
    public void showBuildings() {

    }

    public String statusUnit(String unitType){
        StringBuilder finalString = new StringBuilder();
        for (Soldier troop : troops) {
            if (troop.getClass().getSimpleName().equals(unitType)){
                finalString.append(unitType + " level= " + troop.getLevel() + " in(" + troop.getX() + "," + troop.getY() + ") with health" + troop.getHealth() + "\n");
            }
        }
        return finalString.toString();
    }
    public String statusUnit(){
        StringBuilder finalString = new StringBuilder();
        for (Soldier soldier : Soldier.getSoldierSubClasses()) {
            finalString.append(statusUnit(soldier.getClass().getSimpleName()) + "\n");
        }
        return finalString;
    }
}
