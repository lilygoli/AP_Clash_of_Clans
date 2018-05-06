package com.company.Models.Towers.Buildings;

import com.company.Models.Config;
import com.company.Models.Towers.Buildings.Storage;

public class ElixirStorage extends Storage {
    {
        setCapacity(Config.getDictionary().get("ElixirStorage_CAPACITY"));
    }
    public ElixirStorage(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }
}
