package com.company.Models.Towers.Buildings;

public class GoldMine extends Mine {
    public GoldMine(int number,int level) {
        super(number,level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
        super.setResourceGainSpeed(10);
        super.setResource(0);
    }
}
