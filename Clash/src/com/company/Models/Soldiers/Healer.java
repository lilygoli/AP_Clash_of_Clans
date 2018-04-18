package com.company.Models.Soldiers;

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
}
