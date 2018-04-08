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
    
}
