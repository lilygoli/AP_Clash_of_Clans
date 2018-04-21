package com.company.Models.Soldiers;

import com.company.Models.Village;

public class Dragon extends Soldier {
    public void attackTarget(Village enemyVillage) {
        super.attackTarget(enemyVillage, "all");
    }
}
