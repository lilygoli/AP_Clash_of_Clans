package com.company.Models.Towers.Buildings;

import com.company.Models.Config;

public class ElixirMine extends Mine {
    public ElixirMine(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
        super.setResourceGainSpeed(5);
        super.setResource(0);
    }
}
