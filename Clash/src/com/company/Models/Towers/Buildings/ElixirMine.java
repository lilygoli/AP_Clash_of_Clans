package com.company.Models.Towers.Buildings;

public class ElixirMine extends Mine {
    public ElixirMine(int number,int level) {
        super(number,level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
        super.setResourceGainSpeed(5);
        super.setResource(0);
    }
}
