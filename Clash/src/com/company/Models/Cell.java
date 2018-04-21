package com.company.Models;

import com.company.Main;
import com.company.Models.Buildings.*;
import com.company.Models.Defences.AirDefence;
import com.company.Models.Defences.ArcherTower;
import com.company.Models.Defences.Cannon;
import com.company.Models.Defences.WizardTower;
import com.company.View;

import java.util.ArrayList;

public class Cell {
    //TODO test kardam kar nemikone this tooye const seda zadan ghablan 0 set mishe , avaz beshe

    private final String infoMenu = "1. Overall Info\n2. Upgrade Info\n3. Back";
    private boolean isUnderConstruction;
    private int timeTillConstruction;
    private int x;
    private int y;
    private boolean isRuined;
    private int level;
    private int strength;
    private static ArrayList<Cell> cellKinds=new ArrayList<Cell>();
    static {
        cellKinds.add(new Barrack(0));
        cellKinds.add(new Camp(0));
        cellKinds.add(new ElixirStorage(0));
        cellKinds.add(new GoldStorage(0));
        cellKinds.add(new GoldMine(0));
        cellKinds.add(new ElixirMine(0));
        cellKinds.add(new MainBuilding());
        cellKinds.add(new AirDefence());
        cellKinds.add(new ArcherTower());
        cellKinds.add(new Cannon());
        cellKinds.add(new WizardTower());
    }

    public static ArrayList<Cell> getCellKinds() {
        return cellKinds;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUnderConstruction() {
        return isUnderConstruction;
    }

    public void setUnderConstruction(boolean underConstruction) {
        isUnderConstruction = underConstruction;
    }

    public int getTimeTillConstruction() {
        return timeTillConstruction;
    }

    public void setTimeTillConstruction(int timeTillConstruction) {
        this.timeTillConstruction = timeTillConstruction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isRuined() {
        return isRuined;
    }

    public void setRuined(boolean ruined) {
        isRuined = ruined;
    }

    public void showInfoMenu(){
        View.show(infoMenu);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void showOverallInfo() {
        View.show("Level: " + this.getLevel() + "\nHealth: " + this.getStrength());
    }

    public void showUpgradeInfo() {
        View.show("Upgrade Cost: " + this.getUpgradeCost());
    }

    public static void sortTowers(ArrayList<Cell> towers) {
        if (towers.isEmpty()) {
            return;
        }
        ArrayList<Cell> sortedTowers = new ArrayList<>();
        while(!towers.isEmpty()) {
            Cell min = towers.get(0);
            for (Cell tower : towers) {
                if (tower.getName().compareTo(min.getName()) < 0) { //TODO شاید باید بزرگتر از ۰ باشه!
                    min = tower;
                }
            }
            sortedTowers.add(min);
            towers.remove(min);
        }
        towers = sortedTowers;
    }

    public int getUpgradeCost() {
        return 1;
    }
}
