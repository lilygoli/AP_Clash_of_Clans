package com.company.Models.Buildings;

public class ElixirStorage extends Storage {
    public ElixirStorage(int number,int level) {
        super(number,level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }
}
