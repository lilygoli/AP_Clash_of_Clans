package com.company.Models.Buildings;

public class ElixirMine extends Mine {
    public ElixirMine(int number) {
        super(number);
        super.setResourceGainSpeed(5);
        super.setResource(0);
    }
}
