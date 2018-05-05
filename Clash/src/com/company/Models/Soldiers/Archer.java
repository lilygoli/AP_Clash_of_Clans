package com.company.Models.Soldiers;

import com.company.Models.Towers.Cell;
import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Arrays;

public class Archer extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage){

        super.attackTargets(attackerVillage,enemyVillage ,findDestination(enemyVillage,new ArrayList<String>(Arrays.asList("AirDefence","ArcherTower","Cannon","WizardTower"))));
    }

    public Archer(int time) {
        super(time);
    }
}
