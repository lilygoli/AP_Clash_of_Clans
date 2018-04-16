package com.company.Models.Buildings;

import com.company.View.View;

import java.util.ArrayList;

public class Mine extends Building {
    private int resourceGainSpeed ;
    private int resource = 0;


    public Mine(int number) {
        super(number, 0);
    }

    public int getResourceGainSpeed() {
        return resourceGainSpeed;
    }

    public void setResourceGainSpeed(int resourceGainSpeed) {
        this.resourceGainSpeed = resourceGainSpeed;
    }

    public void upgrade() {
        setLevel(getLevel()+1);
        resourceGainSpeed=(resourceGainSpeed*16)/10;
    }

    @Override
    public void showMenu() {
        String menu = "1. Info\n2. Mine\n3. back";
        View.show(menu);
    }

    public void mine(ArrayList<Storage> allRelatedStorage) { //for any mine we should pass the related array of storage
        for (Storage storage : allRelatedStorage
                ) {
            if (storage.getCapacity() - storage.getResources() >= resource) {
                resource = 0;
                storage.setResources(storage.getResources() + resource);
                return;
            }
        }
    }
}