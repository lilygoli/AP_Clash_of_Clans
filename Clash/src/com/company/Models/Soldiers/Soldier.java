package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Enums.Directon;
import com.company.Models.Cell;

import java.util.ArrayList;

public abstract class Soldier {

    static ArrayList<Soldier> soldierSubClasses;
    private int cost;
    private int buildDuration;
    private int health;
    private int damage;
    private int radius;
    private int maxSpeed;
    private int level;
    private int addedHealth;
    private int addedDamage;
    private int unlockLevel;
    private double x , y;
    boolean canFly;
    private Direction directon;

    public void attackTarget(Cell target, Village enemyVillage) {
        if (hasReachedDestination(enemyVillage)) {
            target.setStrength(target.getStrength() - damage);
            if (target.getStrength() <= 0) {
                target.setRuined(true);
            }
        } else {
            directon = findDirection(enemyVillage, findDestination(enemyVillage));
            moveSoldier(directon);
        }
    }

    private void moveSoldier(Direction directon) {
        if (directon == Direction.LEFT) {
            x = x - MOVE_PER_TURN;
        } else if (directon == Direction.RIGHT) {
            x = x + MOVE_PER_TURN;
        } else if (directon == Direction.UP) {
            y = y + MOVE_PER_TURN;
        } else if (directon == Direction.DOWN) {
            y = y - MOVE_PER_TURN;
        }
    }

    private Cell findDestination(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                    destination = enemyVillage.getMap()[i][j];
                    minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                }
            }
        }
        return destination;
    }

    private Direction findDirection(Village enemyVillage, Cell destination) {
        return Direction.UP;
    }

    private boolean hasReachedDestination(Village enemyVillage) {
        if (Math.sqrt(Math.pow(x - findDestination(enemyVillage).getX(), 2) + Math.pow(y - findDestination(enemyVillage).getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }





}
