package com.company.Models.Defences;

import com.company.Models.Cell;

import java.util.ArrayList;

public abstract class Defence extends Cell {
    private static final String menuOptions = "1. Info\n2. Target\n3. Back";
    private int level;

    public void showMenu() {
        View.show(menuOptions);
    }

    public void showInfoMenu() {

    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {

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

    public int getUpgradeCost() {

    }

}
