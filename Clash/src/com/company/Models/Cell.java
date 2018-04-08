package com.company.Models;

import com.company.View;

public class Cell {
    private final String overalInfo = "Level: " + this.getLevel() + "\nHealth: " + this.getStrength();
    private final String upgradeInfo = "Upgrade Cost: " + this.getUpgradeCost();
    private boolean isUnderConstruction;
    private int timeTillConstruction;
    private int x;
    private int y;
    private boolean isRuined;
    private int level;
    private int strength;

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

    public void showInfoMenu() {

    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void showOveralInfo() {
        View.show(overalInfo);
    }

    public void showUpgradeInfo() {
        View.show(upgradeInfo);
    }

    public static void sortTowers(Cell[] towers) {
        ;
    }

    public int getUpgradeCost() {
        return 1;
    }
}
