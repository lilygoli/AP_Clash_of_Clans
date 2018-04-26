package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Cell;
import com.company.Models.Village;


public class Dragon extends Soldier {
    public void attackTarget(Village enemyVillage) {
        super.attackTarget(enemyVillage, "all");
    }

    @Override
    public Direction findDirection(Village enemyVillage, Cell destination) {
        if (getX() < destination.getX()){
            return Direction.RIGHT;
        }
        else if (getX() > destination.getX()){
            return Direction.LEFT;
        }
        else if (getY() > destination.getY()){
            return Direction.DOWN;
        }
        else if (getY() < destination.getY()){
            return Direction.UP;
        }
        else
            return Direction.NONE;
    }
}
