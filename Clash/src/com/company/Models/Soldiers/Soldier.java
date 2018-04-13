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
    private double x , y;
    private boolean canFly;
    private boolean isDead;
    private Direction directon;

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

    public void upgrade(){
        ;
    }

    public Direction findDirection(Village enemyVillage , Cell destination){
        return Direction.UP;
    }

    public boolean hasReachedDestination(Village enemyVillage){
        if (Math.sqrt(Math.pow(x - findDestination(enemyVillage).getX() , 2) + Math.pow(y - findDestination(enemyVillage).getY() , 2)) <= radius){
            return true;
        }
        return false;
    }

    public Cell findDestination(Village enemyVillage){
        Cell destination = new Cell();
        double minDistance = 100;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j , 2)) < minDistance){
                    destination = enemyVillage.getMap()[i][j];
                    minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j , 2));
                }
            }
        }
        return destination;
    }

    public void moveSoldier(Direction directon , Village enemyVillage){
        directon = findDirection(enemyVillage , findDestination(enemyVillage));
        if (directon == Direction.LEFT){
            x = x - 1/maxSpeed;
        }
        else if (directon == Direction.RIGHT){
            x = x + 1/maxSpeed;
        }
        else if (directon == Direction.UP){
            y = y + 1/maxSpeed;
        }
        else if (directon == Direction.DOWN){
            y = y - 1/maxSpeed;
        }
    }

    public void attackTarget(Cell target){

    }



    
}
