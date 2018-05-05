package com.company.Models.Soldiers;

import com.company.Models.Towers.Cell;
import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Arrays;

public class Giant extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage) {
        super.attackTargets(attackerVillage,enemyVillage ,findDestination(enemyVillage,new ArrayList<>(Arrays.asList("GoldStorage","ElixirStorage","GoldMine","ElixirMine"))));
    }

    public Giant(int time) {
        super(time);
    }
}
