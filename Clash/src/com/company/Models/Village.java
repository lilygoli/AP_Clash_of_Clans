package com.company.Models;

import com.company.Models.Buildings.*;
import com.company.Models.Defences.*;

import java.util.ArrayList;

public class Village {
    private Cell[][] map;
    private int gold;
    private int elixir;
    private int score;
    private ArrayList<ArcherTower> archerTowers;
    private ArrayList<Cannon> cannons;
    private ArrayList<AirDefence> airDefences;
    private ArrayList<Wall> walls;
    private ArrayList<WizardTower> wizardTowers;
    private ArrayList<Trap> traps;
    // TODO: 4/19/2018 add guardian giant arraylist and class
    private ArrayList<GoldMine> goldMines;
    private ArrayList<ElixirMine> elixirMines;
    private MainBuilding mainBuilding;
    private ArrayList<Barrack> barracks;
    private ArrayList<Camp> camps;
    int numOfBuilder;
    int numOfFreeBuilder;

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<ArcherTower> getArcherTowers() {
        return archerTowers;
    }

    public void setArcherTowers(ArrayList<ArcherTower> archerTowers) {
        this.archerTowers = archerTowers;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        this.cannons = cannons;
    }

    public ArrayList<AirDefence> getAirDefences() {
        return airDefences;
    }

    public void setAirDefences(ArrayList<AirDefence> airDefences) {
        this.airDefences = airDefences;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public ArrayList<WizardTower> getWizardTowers() {
        return wizardTowers;
    }

    public void setWizardTowers(ArrayList<WizardTower> wizardTowers) {
        this.wizardTowers = wizardTowers;
    }

    public ArrayList<Trap> getTraps() {
        return traps;
    }

    public void setTraps(ArrayList<Trap> traps) {
        this.traps = traps;
    }

    public ArrayList<GoldMine> getGoldMines() {
        return goldMines;
    }

    public void setGoldMines(ArrayList<GoldMine> goldMines) {
        this.goldMines = goldMines;
    }

    public ArrayList<ElixirMine> getElixirMines() {
        return elixirMines;
    }

    public void setElixirMines(ArrayList<ElixirMine> elixirMines) {
        this.elixirMines = elixirMines;
    }

    public MainBuilding getMainBuilding() {
        return mainBuilding;
    }

    public void setMainBuilding(MainBuilding mainBuilding) {
        this.mainBuilding = mainBuilding;
    }

    public ArrayList<Barrack> getBarracks() {
        return barracks;
    }

    public void setBarracks(ArrayList<Barrack> barracks) {
        this.barracks = barracks;
    }

    public ArrayList<Camp> getCamps() {
        return camps;
    }

    public void setCamps(ArrayList<Camp> camps) {
        this.camps = camps;
    }

    public int getNumOfBuilder() {
        return numOfBuilder;
    }

    public void setNumOfBuilder(int numOfBuilder) {
        this.numOfBuilder = numOfBuilder;
    }

    public int getNumOfFreeBuilder() {
        return numOfFreeBuilder;
    }

    public void setNumOfFreeBuilder(int numOfFreeBuilder) {
        this.numOfFreeBuilder = numOfFreeBuilder;
    }
}
