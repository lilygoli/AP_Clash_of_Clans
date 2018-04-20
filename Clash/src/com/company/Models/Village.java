package com.company.Models;

import com.company.Exception.BusyCellException;
import com.company.Exception.MarginalTowerException;
import com.company.Models.Buildings.*;
import com.company.Models.Defences.*;
import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<GoldStorage> goldStorages;
    private ArrayList<ElixirStorage> elixirStorages;
    private MainBuilding mainBuilding;
    private ArrayList<Barrack> barracks;
    private ArrayList<Camp> camps;
    private int numOfBuilder;
    private int numOfFreeBuilder;

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

    public ArrayList<ElixirStorage> getElixirStorages() {
        return elixirStorages;
    }

    public ArrayList<GoldStorage> getGoldStorages() {
        return goldStorages;
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

    public void buildTower(Cell tower) throws BusyCellException, MarginalTowerException {
        if (tower.getX() <= 0 || tower.getX() >= 29 || tower.getY() <= 0 || tower.getY() >= 29) {
            throw new MarginalTowerException();
        }
        if (!map[tower.getX()][tower.getY()].getClass().isInstance(Grass.class)) {
            map[tower.getX()][tower.getY()] = tower;
        }
        else {
            throw new BusyCellException();
        }
    }

    public String showTownHallStatus() {
        StringBuilder status = new StringBuilder();
        ArrayList<Cell> underConstructionTowers = new ArrayList<>();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.isUnderConstruction()) {
                    underConstructionTowers.add(cell);
                }
            }
            Cell.sortTowers(underConstructionTowers);
            for (Cell underConstructionTower : underConstructionTowers) {
                status.append(underConstructionTower.getName() + underConstructionTower.getTimeTillConstruction());
            }
        }
        return status.toString();
    }


    public String showBarracksStatus(int time) {
        StringBuilder result = new StringBuilder();
        for (Integer timeLeft : sortSoldiersByTimeLeft(time)
                ) {
            for (Barrack barrack : barracks
                    ) {
                for (HashMap<Soldier, Integer> soldierInitialTime : barrack.getUnderConstructionSoldiers()
                        ) {
                    for (Soldier soldier : soldierInitialTime.keySet()) {
                        if (time - soldierInitialTime.get(soldier) == timeLeft) {
                            result.append(soldier.getClass().toString().split(" ")[1]).append(" ").append(timeLeft.toString()).append("\n");
                        }
                    }
                }
            }

        }
        return result.toString().trim();
    }
    private ArrayList<Integer> sortSoldiersByTimeLeft(int time){
        ArrayList<Integer> turnsLeft = new ArrayList<Integer>();
        for (Barrack barrack : barracks
                ) {
            for (HashMap<Soldier, Integer> soldierInitialTime : barrack.getUnderConstructionSoldiers()
                    ) {
                for (Soldier soldier : soldierInitialTime.keySet()) {
                    turnsLeft.add(time - soldierInitialTime.get(soldier));
                }
            }
        }
        turnsLeft.sort((a, b) -> a < b ? a : b);
        return turnsLeft;
    }
    public String showSourcesInfo(){
        //set whereIam
        String result="";
        result=result+"GOLD:"+gold+"\nElixir:"+elixir+"\nScore:"+score;
        return result;
    }
    public String showEnemyMapInfo(Village enemyVillage) {
        //set whereIam
        StringBuilder result= new StringBuilder();
        result.append("Gold: ").append(enemyVillage.getGold()).append("\nElixir: ").append(enemyVillage.getElixir());
        for (Cell cell:Cell.getCellKinds()
                ) {
            result.append("\n").append(cell.getName()).append(": ").append(getNumberOfTower(cell, enemyVillage));
        }
        return result.toString();
    }
    private int getNumberOfTower(Cell cell,Village enemyVillage){
        int number=0;
        switch (cell.getName()){
            case "Camp":number= enemyVillage.getCamps().size();break;
            case "Barrack": number= enemyVillage.getBarracks().size();break;
            case "ElixirStorage":number= enemyVillage.getElixirStorages().size();break;
            case "GoldStorage":number= enemyVillage.getElixirStorages().size();break;
            case "ElixirMine":number= enemyVillage.getElixirMines().size();break;
            case "GoldMine":number= enemyVillage.getGoldMines().size();break;
            case "MainBuilding":number= 1;break;
            case "AirDefense":number= enemyVillage.getAirDefences().size();break;
            case "ArcherTower":number= enemyVillage.getArcherTowers().size();break;
            case "Cannon":number=enemyVillage.getCannons().size();break;
            case "WizardTower":number= enemyVillage.getWizardTowers().size();break;
        }
        return number;
    }

    public String showEnemyMapMenu(){
        //set whereIam
        String result="1. Map Info\n2. Attack map\n3. Back";
        return result;
    }
}
