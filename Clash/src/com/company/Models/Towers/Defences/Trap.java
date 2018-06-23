package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Cell;

import java.util.ArrayList;

public class Trap extends Defence{
    public Trap(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
    }

    @Override
    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        return null;
    }
}
