package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Towers.Cell;

public class Trap extends Cell{
    public Trap(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
    }
}
