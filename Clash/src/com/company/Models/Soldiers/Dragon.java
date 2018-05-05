package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Towers.Cell;
import com.company.Models.Village;


public class Dragon extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage) {
        super.attackTargets(attackerVillage,enemyVillage,findDestination(enemyVillage,getAllValidDestinations()));
    }

    public Dragon(int time) {
        super(time);
    }
}
