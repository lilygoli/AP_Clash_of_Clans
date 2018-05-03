package com.company.Models.Soldiers;

import com.company.Models.Village;

public class Archer extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage){
        super.attackTargets(attackerVillage,enemyVillage , "Defence");
    }

    public Archer(int time) {
        super(time);
    }
}
