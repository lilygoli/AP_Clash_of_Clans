package com.company.Models;

public abstract class Cell {
    private boolean isUnderConstruction;
    private int timeTillConstruction;
    private int x;
    private int y;
    private boolean isRuined;

    public abstract void showInfoMenu();

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void showOveralInfo() {

    }

    public void showUpgradeInfo() {

    }

    public static void sortTowers(Cell[] towers) {

    }
}
