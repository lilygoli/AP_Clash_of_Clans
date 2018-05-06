package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class Cannon extends Defence {
    public Cannon(int number,int level) {
        super(number, level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        if (this.getUnderConstructionStatus()) {
            return null;
        }
        ArrayList<Integer> validManhattanDistance = new ArrayList<>();
        validManhattanDistance.add(0);
        validManhattanDistance.add(1);
        Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
        if (target != null) {
            for (Soldier enemySoldier : enemySoldiers) {
                if (enemySoldier.getCanFly()) {
                    continue;
                }
                Integer manhatanDistance =(int)Math.abs(enemySoldier.getX() - target.getX()) + (int)Math.abs(enemySoldier.getY() - target.getY());
                if (validManhattanDistance.contains(manhatanDistance)) {
                    enemySoldier.setHealth(enemySoldier.getHealth() - this.getDamage());
                    if (target.getHealth() <= 0) {
                        enemySoldiers.remove(target);
                    }
                }
            }
        }
        return target;
    }
}
