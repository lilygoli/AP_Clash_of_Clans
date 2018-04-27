package com.company.Models.Defences;

import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class AirDefence extends Defence {
    public AirDefence(int number,int level) {
        super(number,level);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        Soldier target = findNearestEnemyInRange(enemySoldiers, true, false);
        if (target != null) {
            target.setHealth(target.getHealth() - this.getDamage());
            if (target.getHealth() <= 0) {
                enemySoldiers.remove(target);
            }
        }
        return target;
    }
}
