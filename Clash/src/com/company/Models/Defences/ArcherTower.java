package com.company.Models.Defences;

import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class ArcherTower extends Defence {
    public ArcherTower(int number,int level) {
        super(number, level);
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
        if (target != null) {
            target.setHealth(target.getHealth() - this.getDamage());
        }
        return target;
    }
}
