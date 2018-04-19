package com.company.Models.Buildings;

import com.company.View;

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

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
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
                storage.addToStorage(resource);
                resource = 0;
                return;
            }
        }
    }
    public void addToMine(ArrayList<Storage> allRelatedStorage){
        int flag=0;
        for (Storage storage:allRelatedStorage
             ) {
            if(storage.getCapacity()-storage.getResources()!=0){
                flag=1;
            }
        }
        if(flag==1){
            this.setResource(this.getResource()+this.getResourceGainSpeed());
        }
    }
}