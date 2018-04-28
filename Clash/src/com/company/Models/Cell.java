package com.company.Models;

import com.company.Models.Buildings.*;
import com.company.Models.Defences.AirDefence;
import com.company.Models.Defences.ArcherTower;
import com.company.Models.Defences.Cannon;
import com.company.Models.Defences.WizardTower;
import com.company.View;

import java.util.ArrayList;

public class Cell {

    private final String infoMenu = "1. Overall Info\n2. Upgrade Info\n3. Back";
    private boolean isUnderConstruction;
    private int timeTillConstruction;
    private int x;
    private int y;
    private int jsonType;
    private boolean isRuined;
    private int level;
    private int strength;
    private int amount;
    private static ArrayList<Cell> cellKinds=new ArrayList<>();
    private int number;
    private Builder workingBuilder;
    private int timeLeftOfConstruction;

    public Cell(int number,int level){
        Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH");
        this.level=level;
        this.number=number;
    }

    public Builder getWorkingBuilder() {
        return workingBuilder;
    }

    public void setWorkingBuilder(Builder workingBuilder) {
        this.workingBuilder = workingBuilder;
    }
    static {
        cellKinds.add(new Barrack(0,0));
        cellKinds.add(new Camp(0,0));
        cellKinds.add(new ElixirStorage(0,0));
        cellKinds.add(new GoldStorage(0,0));
        cellKinds.add(new GoldMine(0,0));
        cellKinds.add(new ElixirMine(0,0));
        cellKinds.add(new MainBuilding(0));
        cellKinds.add(new AirDefence(0,0));
        cellKinds.add(new ArcherTower(0,0));
        cellKinds.add(new Cannon(0,0));
        cellKinds.add(new WizardTower(0,0));
    }

    public void upgrade() {
        //it is overRidden in its children
    }

    public int getAmount() {
        return amount;
    }

    public int getJsonType() {
        return jsonType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public boolean getUnderConstrctionStatus() {
        return isUnderConstruction;
    }

    public void setUnderConstructionStatus(boolean underConstruction) {
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
        Game.setWhereIAm("You are in"+this.getClass().getSimpleName()+"info menu");
        View.show(infoMenu);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void showOverallInfo() {
        Game.setWhereIAm("You are in"+this.getClass().getSimpleName()+"Overall info menu");
        View.show("Level: " + this.getLevel() + "\nHealth: " + this.getStrength());
    }

    public void showUpgradeInfo() {
        Game.setWhereIAm("You are in"+this.getClass().getSimpleName()+"upgrade info menu");
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
                else if (tower.getName().compareTo(min.getName()) == 0) {
                    if (min.getNumber() > tower.getNumber()) {
                        min = tower;
                    }
                }
            }
            sortedTowers.add(min);
            towers.remove(min);
        }
        towers.addAll(sortedTowers);
    }

    public int getUpgradeCost() {
        return 1;
    }
    public void setTimeLeftOfConstruction(int timeLeftOfConstruction) {
        this.timeLeftOfConstruction = timeLeftOfConstruction;
    }

    public int getTimeLeftOfConstruction() {
        return timeLeftOfConstruction;
    }

    public void showMenu() {

    }
}
