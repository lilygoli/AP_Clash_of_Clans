package com.company.Models.Soldiers;

import com.company.Models.Village;

public class Giant extends Soldier {
    public void attackTarget(Village attackerVillage,Village enemyVillage) {
        super.attackTargets(attackerVillage,enemyVillage , "Storage");
    }
}
