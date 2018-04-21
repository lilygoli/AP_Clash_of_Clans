package com.company.Models.Buildings;

public class ElixirMine extends Mine {
    public ElixirMine(int number,int level) {
        super(number,level);
        super.setResourceGainSpeed(5);
        super.setResource(0);
    }
}
