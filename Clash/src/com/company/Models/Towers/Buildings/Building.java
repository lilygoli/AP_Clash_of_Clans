package com.company.Models.Towers.Buildings;

import com.company.Models.Towers.Cell;
import com.company.Models.Config;

public abstract class Building extends Cell {

    private static String[] nameOfChildren = new String[]{"Barrack", "Camp", "ElixirMine", "GoldMine", "GoldStorage", "ElixirStorage"};

    public Building(int number, int level) {
        super(number, level);
    }

    public int getBuildDuration() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION");
    }

    public int getStrength() {
        return getStrength();
    }

    public int getJsonType() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_JSON_TYPE");
    }

    public int getCapacity() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_CAPACITY");
    }

    public static String[] getNameOfChildren() {
        return nameOfChildren;
    }


    public abstract void upgrade();

    public abstract void showMenu();


}
