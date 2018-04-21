package com.company.Models.Soldiers;

import com.company.Models.Village;

public class Guardian extends Soldier{
    public void attackTarget(Village enemyVillage) {
        super.attackTarget(enemyVillage, "all");
    }
}
