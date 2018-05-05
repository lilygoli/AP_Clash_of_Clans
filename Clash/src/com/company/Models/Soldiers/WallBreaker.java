package com.company.Models.Soldiers;

import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Arrays;

public class WallBreaker extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage){
        super.attackTargets(attackerVillage,enemyVillage ,findDestination(enemyVillage,new ArrayList<>(Arrays.asList("Wall"))));
    }

    public WallBreaker(int time) {
        super(time);
    }
}
