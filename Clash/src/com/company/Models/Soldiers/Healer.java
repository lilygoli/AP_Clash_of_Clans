package com.company.Models.Soldiers;

import com.company.Models.Config;

public class Healer extends Soldier {

    private int heal;
    private int healAdded;

    public int getHeal() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_HEAL");
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getHealAdded() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_HEAL");
    }

    public void setHealAdded(int healAdded) {
        this.healAdded = healAdded;
    }

    @Override
    public void upgrade(){
        heal += healAdded;
    }

    // TODO: 4/26/2018 complete healer for phase 2
}
