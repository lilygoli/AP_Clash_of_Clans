package com.company.Models.Towers.Buildings;

import com.company.Models.Config;

public class GoldStorage extends Storage {
    {
        setCapacity(Config.getDictionary().get("GoldStorage_CAPACITY"));
    }
    public GoldStorage(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }
}
