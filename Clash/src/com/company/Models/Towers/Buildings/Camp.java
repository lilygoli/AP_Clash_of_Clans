package com.company.Models.Towers.Buildings;

import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.View.View;

import java.util.ArrayList;
import java.util.HashMap;

public class Camp extends Building {

    private ArrayList<Soldier> soldiers=new ArrayList<Soldier>();

    public Camp(int number,int level) {
        super(number, level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    @Override
    public void upgrade() {
       //cant be upgraded
    }

    @Override
    public void showMenu() {
        Game.setWhereIAm("You are in Camp Menu");
        String menu = "1. Info\n2. Soldiers\n3. back";
        View.show(menu);
    }
    public void showInfoMenu(){
        Game.setWhereIAm("You are in Camp info menu");
        String infoMenu = "1. Overall info\n2. Upgrade info\n3. Capacity info\n4. upgrade\n5. Back";
        View.show(infoMenu);
    }
    public void showCapacityInfo(ArrayList<Camp> camps){
        Game.setWhereIAm("You are in Camp capacity info Menu");
        int numberOfSoldiers=0;
        for (Camp camp : camps) {
            numberOfSoldiers+=camp.getSoldiers().size();
        }
        String capacityInfo="Your camps capacity is "+numberOfSoldiers+"/"+camps.size()*getCapacity()+".";
        View.show(capacityInfo);
    }
    public String  showSoldiers(){
        HashMap<Class,Integer> soldierClasses=matchEachSoldierTypeWithNumber();
        StringBuilder soldierList= new StringBuilder();
        for (Class soldierClass: soldierClasses.keySet()
             ) {
            soldierList.append(soldierClass.getSimpleName()).append(" x").append(soldierClasses.get(soldierClass)).append("\n");
        }
        soldierList = new StringBuilder(soldierList.toString().trim());
        return soldierList.toString();

    }
    private HashMap<Class,Integer> matchEachSoldierTypeWithNumber(){
        HashMap<Class,Integer> soldierClasses=new HashMap<Class,Integer>();
        for (Soldier soldier: soldiers
                ) {
            if(!soldierClasses.containsKey(soldier.getClass())){
                soldierClasses.put(soldier.getClass(),findNumberOfCertainSoldierType(soldier.getClass()));
            }
        }
        return soldierClasses;
    }
    private int findNumberOfCertainSoldierType(Class soldierClass){
        int number=0;
        for (Soldier soldier:soldiers
             ) {
            if(soldier.getClass()==soldierClass){
                number++;
            }
        }
        return number;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void removeSoldier(Soldier soldier) {
        this.soldiers.remove(soldier);
    }
}
