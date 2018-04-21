package com.company.Models.Buildings;

import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;
import java.util.HashMap;

public class Camp extends Building {

    private ArrayList<Soldier> soldiers=new ArrayList<Soldier>();

    public Camp(int number) {
        super(number, 0);
    }

    @Override
    public void upgrade() {
       //cant be upgraded
    }

    @Override
    public void showMenu() {
        Game.setWhereIam("You are in Camp Menu");
        String menu = "1. Info\n2. Soldiers\n3. back";
        View.show(menu);
    }
    public void showInfoMenu(){
        Game.setWhereIam("You are in Camp info menu");
        String infoMenu = "1. Overall info\n2. Upgrade info\n3. Capacity info\n4. Back";
        View.show(infoMenu);
    }
    public void showCapacityInfo(int numberOfSoldiers,int numberOfCamps){
        Game.setWhereIam("You are in Camp capacity info Menu");
        String capacityInfo="Your camps capacity is "+numberOfSoldiers+"/"+numberOfCamps*getCapacity()+".";
        View.show(capacityInfo);
    }
    public void showSoldiers(){
        HashMap<Class,Integer> soldierClasses=matchEachSoldierTypeWithNumber();
        StringBuilder soldierList= new StringBuilder();
        for (Class soldierClass: soldierClasses.keySet()
             ) {
            soldierList.append(soldierClass.toString().split(" ")[1]).append(" x").append(soldierClasses.get(soldierClass)).append("\n");
        }
        soldierList = new StringBuilder(soldierList.toString().trim());
        View.show(soldierList.toString());

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
    public void healCamp(){
        for (Soldier soldier:soldiers
             ) {
            //soldier.heal implemented in soldier class
        }
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

}
