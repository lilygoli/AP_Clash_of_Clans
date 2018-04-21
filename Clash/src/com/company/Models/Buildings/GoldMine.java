package com.company.Models.Buildings;

public class GoldMine extends Mine {
    public GoldMine(int number,int level) {
        super(number,level);
        super.setResourceGainSpeed(10);
        super.setResource(0);
    }
}
