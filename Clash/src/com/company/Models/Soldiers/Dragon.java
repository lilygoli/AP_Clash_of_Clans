package com.company.Models.Soldiers;

import com.company.Controller.Controller;
import com.company.Enums.Direction;
import com.company.Models.Towers.Cell;
import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Arrays;


public class Dragon extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage) {
        super.attackTargets(attackerVillage,enemyVillage,findDestination(enemyVillage, new ArrayList<>(Arrays.asList("AirDefence","ArcherTower","Cannon","WizardTower" , "Barrack" , "Camp" , "ElixirMine" , "ElixirStorage" , "GoldMine" , "GoldStorage" , "MainBuilding"))));
    }

    public Dragon(int time) {
        super(time);
    }
}
