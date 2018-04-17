package com.company.Models.Buildings;

import com.company.Models.Soldier.Soldier;
import com.company.View.View;

import java.util.ArrayList;

public class Camp extends Building {

    private static final int capacity=50;
    private ArrayList<Soldier> soldiers=new ArrayList<Soldier>();

    public Camp(int number) {
        super(number, 0);
    }

    @Override
    public void upgrade() {
        //cannot be upgraded
    }

    @Override
    public void showMenu() {
        String menu = "1. Info\n2. Soldiers\n3. back";
        View.show(menu);
    }
    public void showInfoMenu(){
        String infoMenu = "1. Overall info\n2. Upgrade info\n3. Capacity info\n4. Back";
        View.show(infoMenu);
    }
}
