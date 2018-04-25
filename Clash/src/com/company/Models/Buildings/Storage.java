package com.company.Models.Buildings;

import com.company.Models.Game;
import com.company.View;

public class Storage extends Building {
    private int capacity;
    private int resource;

    public Storage(int number,int level) {
        super(number,level);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
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
        Game.setWhereIAm("You are in Storage Menu");
        String menu = "1. Info";
        View.show(menu);
    }

    public void showInfoMenu() {
        String menu = "1. Overall info\n2. Upgrade info\n3. Sources info\n4. upgrade\n5. back";
        View.show(menu);
    }

    public void addToStorage(int amount) {
        if (capacity - resource >= amount) {
            resource += amount;
        }else{
            resource =capacity;
        }

    }

}
