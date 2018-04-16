package com.company.Models.Soldiers;

import com.company.Enums.Direction;

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
    private double x, y;
    private boolean canFly;
    private boolean isDead;
    private Direction directon;

    private final double MOVE_PER_TURN = 1 / maxSpeed;
    //private distanceCalc()

    public static ArrayList<Soldier> getSoldierSubClasses() {
        return soldierSubClasses;
    }

    public int getCost() {
        return cost;
    }

    public int getBuildDuration() {
        return buildDuration;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getRadius() {
        return radius;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getLevel() {
        return level;
    }

    public int getAddedHealth() {
        return addedHealth;
    }

    public int getAddedDamage() {
        return addedDamage;
    }

    public int getUnlockLevel() {
        return unlockLevel;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public boolean isDead() {
        return isDead;
    }

    public Direction getDirecton() {
        return directon;
    }

    public void upgrade() {
        damage += addedDamage;
        health += addedHealth;
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
}
