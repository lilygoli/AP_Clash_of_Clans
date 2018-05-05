package com.company.Models.Towers.Buildings;

import com.company.Models.Game;
import com.company.View.View;

import java.util.ArrayList;

public class Storage extends Building {
    private int capacity = 500;
    // TODO: 4/29/2018 what is capacity 500 or 10000
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
        String menu = "1. Info\n2. back";
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
    public String getSourcesInfo(ArrayList<Storage> storages,String nameOfStorage){
        int amount=0;
        int capacity=0;
        for (Storage storage : storages) {
            amount+=storage.getResource();
            capacity+=storage.getCapacity();
        }
        return "Your " + nameOfStorage + " is " + amount + "/" + capacity + " loaded.";

    }
    public boolean isFull(){
        return resource >= capacity;
    }

}
