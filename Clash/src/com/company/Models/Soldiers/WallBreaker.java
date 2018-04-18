package com.company.Models.Soldiers;

import com.company.Models.Cell;
import com.company.Models.Village;

public class WallBreaker extends Soldier {
    public void attackTarget(Village enemyVillage){
        super.attackTarget(enemyVillage , "Wall");
    }
}
