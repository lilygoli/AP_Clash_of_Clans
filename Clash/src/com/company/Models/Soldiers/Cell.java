package com.company.Models.Soldiers;

public class Cell {
    private boolean isUnderConstruction;
    private int timeTillConstruction;
    private int x;
    private int y;

    public boolean isRuined() {
        return isRuined;
    }

    public void setRuined(boolean ruined) {
        isRuined = ruined;
    }

    private boolean isRuined;
    private int strength;

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
