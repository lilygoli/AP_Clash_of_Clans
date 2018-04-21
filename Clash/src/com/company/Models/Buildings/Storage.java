package com.company.Models.Buildings;

import com.company.Models.Game;
import com.company.View;

public class Storage extends Building {
    private int capacity;
    private int resources;

    public Storage(int number) {
        super(number, 0);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getResources() {
        return resources;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void upgrade() {
        setLevel(getLevel()+1);
        capacity=(capacity*16)/10;
    }

    @Override
    public void showMenu() {
        Game.setWhereIam("You are in Storage Menu");
        String menu = "1. Info";
        View.show(menu);
    }

    public void showInfoMenu() {
        String menu = "1. Overall info\n2. Upgrade info\n3. Sources info\n4. upgrade\n5. back";
        View.show(menu);
    }

    public void addToStorage(int amount) {
        if (capacity - resources >= amount) {
            resources += amount;
        }else{
            resources=capacity;
        }

    }

}
