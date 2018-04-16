package com.company.Models.Buildings;

public class Storage extends Building {
    private int capacity;
    private int resources;

    public Storage(int number, int level) {
        super(number, level);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void showMenu() {

    }

    public int getResources() {
        return resources;
    }
}
