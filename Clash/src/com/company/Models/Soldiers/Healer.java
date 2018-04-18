package com.company.Models.Soldiers;

public class Healer extends Soldier {

    private int heal;
    private int healAdded;

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getHealAdded() {
        return healAdded;
    }

    public void setHealAdded(int healAdded) {
        this.healAdded = healAdded;
    }

    @Override
    public void upgrade(){
        heal += healAdded;
    }
}
