package com.company.Models.Buildings;

public class GoldMine extends Mine {
    public GoldMine(int number) {
        super(number);
        super.setResourceGainSpeed(10);
        super.setResource(0);
    }
}
