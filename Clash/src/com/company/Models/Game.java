package com.company.Models;

import com.company.Models.Buildings.Grass;
import com.company.Models.Buildings.MainBuilding;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;

import com.company.View;

public class Game {
    private String playerName;
    private Village village;
    private int time;
    private boolean isUnderAttackOrVillage;
    private Game attackedVillage=null;
    private static String whereIAm;
    private ArrayList<Soldier> troops;
    private static String whereIam="You are in village";
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
        ArrayList<Cell> buildings = new ArrayList<>();
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (!cell.getClass().isInstance(Grass.class)) {
                    buildings.add(cell);
                }
            }
            Cell.sortTowers(buildings);
            for (Cell building : buildings) {
                View.show(building.getName() + building.getNumber());
            }
        }
    }

    public static String getWhereIam() {
        return whereIam;
    }

    public static void setWhereIam(String whereIam) {
        Game.whereIam = whereIam;
    }

    public String showEnemyMapInfo(Village enemyVillage) {
        Game.setWhereIam("You are in enemy map menu");
        StringBuilder result= new StringBuilder();
        result.append("Gold: ").append(enemyVillage.getGold()).append("\nElixir: ").append(enemyVillage.getElixir());
        for (Cell cell:Cell.getCellKinds()
                ) {
            result.append("\n").append(cell.getName()).append(": ").append(getNumberOfTower(cell, enemyVillage));
        }
        return result.toString();
    }
    private int getNumberOfTower(Cell cell,Village enemyVillage){
        int number=0;
        switch (cell.getName()){
            case "Camp":number= enemyVillage.getCamps().size();break;
            case "Barrack": number= enemyVillage.getBarracks().size();break;
            case "ElixirStorage":number= enemyVillage.getElixirStorages().size();break;
            case "GoldStorage":number= enemyVillage.getElixirStorages().size();break;
            case "ElixirMine":number= enemyVillage.getElixirMines().size();break;
            case "GoldMine":number= enemyVillage.getGoldMines().size();break;
            case "MainBuilding":number= 1;break;
            case "AirDefense":number= enemyVillage.getAirDefences().size();break;
            case "ArcherTower":number= enemyVillage.getArcherTowers().size();break;
            case "Cannon":number=enemyVillage.getCannons().size();break;
            case "WizardTower":number= enemyVillage.getWizardTowers().size();break;
        }
        return number;
    }

    public String showEnemyMapMenu(){//whereIam should be changed back to you are in village where this is called
        whereIam="EnemyMapMenu";
        return "1. Map Info\n2. Attack map\n3. Back";
    }
    public void showWhereIAm(){
        View.show(whereIam);
    }

    public void rebuild() {
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().isInstance(MainBuilding.class)) {
                    cell.setStrength(Config.getDictionary().get(this.getClass().getSimpleName() + "_STRENGTH") + cell.getLevel() * 500);
                }
                if (!cell.getClass().isInstance(Grass.class) && cell.isRuined()) {
                    cell.setRuined(false);
                }
                cell.setStrength(Config.getDictionary().get(this.getClass().getSimpleName() + "_STRENGTH"));
            }
        }
    }
}
