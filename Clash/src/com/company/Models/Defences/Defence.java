package com.company.Models.Defences;

import com.company.Models.Cell;

import java.util.ArrayList;

public abstract class Defence extends Cell {
    private static final String menuOptions = "1. Info\n2. Target\n3. Back";
    private static final String infoMenuOptions = "1. Overall info\n2. Upgrade info\n3. Attack info\n4. Back";
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void showMenu() {
        View.show(menuOptions);
    }

    public void showInfoMenu() {
        View.show(infoMenuOptions);
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        double minDistance = -1;
        Soldier target;
        for(Soldier soldier : enemySoldiers) {
            double deltaX = (soldier.getX() - this.getX()) * (soldier.getX() - this.getX());
            double deltaY = (soldier.getY() - this.getY()) * (soldier.getY() - this.getY());
            double distance = Math.sqrt(deltaX * deltaX - deltaY * deltaY);
            if (distance < minDistance && distance < this.getRadius()) {
                target = soldier;
                minDistance = distance;
            }
        }
        if (target != null) {
            //TODO shoot enemy
        }
        return target;
    }

    public int getJsonType() {

    }

    public int getCost() {

    }

    public int getBuildDuration() {

    }

    public int getStrength() {

    }

    public int getDamage() {

    }

    public int getRadius() {

    }

    public int getPointsGainedWhenDistructed() {

    }

    public int getResourceGainedWhenDistructed() {

    }
}
