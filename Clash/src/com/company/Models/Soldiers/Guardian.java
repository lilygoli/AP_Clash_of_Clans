package com.company.Models.Soldiers;

import com.company.Models.Towers.Cell;
import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Arrays;

public class Guardian extends Soldier{
    public void attackTarget(Village attackerVillage,Village enemyVillage) {

        super.attackTargets(attackerVillage,enemyVillage,findDestination(enemyVillage,getAllValidDestinations()));

    }

    public Guardian(int time) {
        super(time);
    }
}
