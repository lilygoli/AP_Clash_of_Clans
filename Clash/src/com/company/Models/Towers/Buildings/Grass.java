package com.company.Models.Towers.Buildings;

public class Grass extends Building{
    {
        this.setUnderConstructionStatus(false);
    }

    public Grass() {
        super(0, 0);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void showMenu() {

    }
}
