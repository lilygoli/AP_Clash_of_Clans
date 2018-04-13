package com.company.Models.Soldiers;

import com.company.Enums.Directon;

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
    private Directon directon;

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

    public Directon getDirecton() {
        return directon;
    }

    public void upgrade(){

    }

    public Directon findDirection(Village enemyVillage , Cell destination){

    }

    public boolean hasReachedDestination(){
        if (Math.sqrt(Math.pow(x - findDestination().x , 2) + Math.pow(y - findDestination().y , 2)) < radius){
            return true;
        }
        return false;
    }

    public Cell findDestination(){
        Cell cell = new Cell();
        return cell;
    }

    public void moveSoldier(Directon directon){
        if (directon == Directon.UP){
            y = y + maxSpeed;
        }
        else if (directon == Directon.DOWN){
            this.y = this.y - thi;
        }
    }

    public void attackTarget(Cell target){

    }



    
}
