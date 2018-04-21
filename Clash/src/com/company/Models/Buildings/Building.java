package com.company.Models.Buildings;

import com.company.Models.Cell;
import com.company.Models.Config;

import java.util.Properties;

public abstract class Building extends Cell {

private int number;
private int timeLeftOfConstruction;
private static String[] nameOfChildren=new String[]{"Barrack","Camp","ElixirMine","GoldMine","GoldStorage","ElixirStorage"};

    public Building(int number,int level){
        super.setLevel(level);
        this.number=number;
    }

    public int getGoldCost() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_GOLD_COST");
    }

    public int getElixirCost() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_ELIXIR_COST");
    }

    public int getBuildDuration() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_BUILD_DURATION");
    }

    public int getStrength() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH");
    }

    public int getPointsGainedWhenDestructed() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_POINTS_GAINED_WHEN_DESTRUCTED");
    }

    public int getGoldGainedWhenDestructed() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_GOLD_GAINED_WHEN_DESTRUCTED");
    }

    public int getElixirGainedWhenDestructed() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_ELICIR_GAINED_WHEN_DESTRUCTED");
    }

    public int getJsonType() {
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_JSON_TYPE");
    }
    public int getCapacity(){
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_CAPACITY");
    }
    public int getUpgradeCost(){
        if (this.getClass().getSimpleName().equals("MainBuilding")){
            return Config.getDictionary().get(this.getClass().getSimpleName()+"_UPGRADE_POINTS_NEEDED");
        }
        return Config.getDictionary().get(this.getClass().getSimpleName()+"_UPGRADE_COST");
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static String[] getNameOfChildren() {
        return nameOfChildren;
    }

    public void setTimeLeftOfConstruction(int timeLeftOfConstruction) {
        this.timeLeftOfConstruction = timeLeftOfConstruction;
    }

    public int getTimeLeftOfConstruction() {
        return timeLeftOfConstruction;
    }

    public abstract void upgrade();

    public abstract void showMenu();


}
