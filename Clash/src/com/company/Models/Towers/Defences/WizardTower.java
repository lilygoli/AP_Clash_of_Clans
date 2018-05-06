package com.company.Models.Towers.Defences;


import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class WizardTower extends Defence {
    public WizardTower(int number,int level) {
        super(number,level);
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
        Soldier target = findNearestEnemyInRange(enemySoldiers, true, true);
        if (target != null) {
            for (Soldier enemySoldier : enemySoldiers) {
                Integer manhattanDistance =(int)Math.abs(enemySoldier.getX() - target.getX()) + (int)Math.abs(enemySoldier.getY() - target.getY());
                if (validManhattanDistance.contains(manhattanDistance)) {
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
