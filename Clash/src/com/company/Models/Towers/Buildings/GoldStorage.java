package com.company.Models.Towers.Buildings;

public class GoldStorage extends Storage {
    public GoldStorage(int number,int level) {
        super(number,level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }
}
