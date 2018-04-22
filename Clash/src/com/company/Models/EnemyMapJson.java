package com.company.Models;

import com.company.Models.Buildings.*;
import com.company.Models.Cell;
import com.company.Models.Defences.*;
import com.company.Models.Game;
import com.company.Models.Resource;
import java.util.ArrayList;

public class EnemyMapJson {
    private static final int mapSize = 30;
    private ArrayList<Integer> size = null;
    private ArrayList<Wall> walls = null;
    private Resource resources;
    private ArrayList<Cell> buildings = null;

    public ArrayList<Integer> getSize() {
        return size;
    }

    public void setSize(ArrayList<Integer> size) {
        this.size = size;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public Resource getResources() {
        return resources;
    }

    public void setResources(Resource resources) {
        this.resources = resources;
    }

    public ArrayList<Cell> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Cell> buildings) {
        this.buildings = buildings;
    }

    public Game ConvertEnemyJsonToGame(){ //name player bayad tooye loadMap az roo file mikhunim barabare name file gharar begire
        Game game=new Game();
        game.setVillage(new Village());
        game.setTime(0);
        game.setAttackStatus(true);
        game.getVillage().setWalls(walls);
        game.getVillage().setResource(resources);
        for (Cell building:buildings
             ) {
            switch (building.getJsonType()){
                case 1:initializeGoldMine(game,building);break;
                case 2:initializeElixirMine(game,building);break;
                case 3: initializeGoldStorage(game,building);break;
                case 4:initializeElixirStorage(game,building);break;
                case 5:initializeMainBuilding(game,building);break;
                case 6:initializeBarrack(game,building); break;
                case 7: initializeCamp(game,building);break;
                case 8:initializeArcherTower(game,building);break;
                case 9:initializeCannon(game,building);break;
                case 10: initializeAirDefense(game,building);break;
                case 11: initializeWizardTower(game,building);break;
                case 14:
                    //Gaurdian Giant
                    break;
            }
        }
        return game;
    }
    public EnemyMapJson convertGameToEnemyMapJson(Game game){
        EnemyMapJson enemyMapJson=new EnemyMapJson();
        size.add(mapSize);
        size.add(mapSize);
        walls.addAll(game.getVillage().getWalls());
        resources=game.getVillage().getResource();
        buildings.addAll(game.getVillage().getAirDefences());
        buildings.addAll(game.getVillage().getArcherTowers());
        buildings.addAll(game.getVillage().getBarracks());
        buildings.addAll(game.getVillage().getCamps());
        buildings.addAll(game.getVillage().getCannons());
        buildings.addAll(game.getVillage().getElixirMines());
        buildings.addAll(game.getVillage().getElixirStorages());
        buildings.addAll(game.getVillage().getGoldMines());
        buildings.addAll(game.getVillage().getGoldStorages());
        buildings.addAll(game.getVillage().getWizardTowers());
        buildings.add(game.getVillage().getMainBuilding());
        return enemyMapJson;

    }
    private void initializeGoldMine(Game game,Cell building){
        GoldMine goldMine= new GoldMine(game.getVillage().getGoldMines().size(),building.getLevel());
        goldMine.setX(building.getX());
        goldMine.setY(building.getY());
        game.getVillage().getGoldMines().add(goldMine);
        game.getVillage().getMap()[building.getX()][building.getY()]=goldMine;
    }
    private void initializeElixirMine(Game game,Cell building){
        ElixirMine elixirMine= new ElixirMine(game.getVillage().getElixirMines().size(),building.getLevel());
        elixirMine.setX(building.getX());
        elixirMine.setY(building.getY());
        game.getVillage().getElixirMines().add(elixirMine);
        game.getVillage().getMap()[building.getX()][building.getY()]=elixirMine;
    }
    private void initializeGoldStorage(Game game,Cell building){
        GoldStorage goldStorage= new GoldStorage(game.getVillage().getGoldStorages().size(),building.getLevel());
        goldStorage.setX(building.getX());
        goldStorage.setY(building.getY());
        goldStorage.setResource(building.getAmount());
        game.getVillage().getGoldStorages().add(goldStorage);
        game.getVillage().getMap()[building.getX()][building.getY()]=goldStorage;
    }
    private void initializeElixirStorage(Game game,Cell building){
        ElixirStorage elixirStorage= new ElixirStorage(game.getVillage().getElixirStorages().size(),building.getLevel());
        elixirStorage.setX(building.getX());
        elixirStorage.setY(building.getY());
        elixirStorage.setResource(building.getAmount());
        game.getVillage().getElixirStorages().add(elixirStorage);
        game.getVillage().getMap()[building.getX()][building.getY()]=elixirStorage;
    }
    private  void initializeMainBuilding(Game game,Cell building){
        MainBuilding mainBuilding= new MainBuilding(building.getLevel());
        mainBuilding.setX(building.getX());
        mainBuilding.setY(building.getY());
        mainBuilding.setStrength(building.getLevel()*500+1000);
        game.getVillage().setMainBuilding(mainBuilding);
        game.getVillage().getMap()[building.getX()][building.getY()]=mainBuilding;
    }
    private  void initializeBarrack(Game game,Cell building){
        Barrack barrack= new Barrack(game.getVillage().getBarracks().size(),building.getLevel());
        barrack.setX(building.getX());
        barrack.setY(building.getY());
        game.getVillage().getBarracks().add(barrack);
        game.getVillage().getMap()[building.getX()][building.getY()]=barrack;
    }
    private void initializeCamp(Game game,Cell building){
        Camp camp= new Camp(game.getVillage().getCamps().size(),building.getLevel());
        camp.setX(building.getX());
        camp.setY(building.getY());
        game.getVillage().getCamps().add(camp);
        game.getVillage().getMap()[building.getX()][building.getY()]=camp;
    }
    private void initializeArcherTower(Game game,Cell building){
        ArcherTower archerTower= new ArcherTower(game.getVillage().getArcherTowers().size(),building.getLevel());
        archerTower.setX(building.getX());
        archerTower.setY(building.getY());
        game.getVillage().getArcherTowers().add(archerTower);
        game.getVillage().getMap()[building.getX()][building.getY()]=archerTower;
    }
    private void initializeCannon(Game game,Cell building){
        Cannon cannon= new Cannon(game.getVillage().getCannons().size(),building.getLevel());
        cannon.setX(building.getX());
        cannon.setY(building.getY());
        game.getVillage().getCannons().add(cannon);
        game.getVillage().getMap()[building.getX()][building.getY()]=cannon;
    }
    private void initializeAirDefense(Game game,Cell building){
        AirDefence airDefence= new AirDefence(game.getVillage().getAirDefences().size(),building.getLevel());
        airDefence.setX(building.getX());
        airDefence.setY(building.getY());
        game.getVillage().getAirDefences().add(airDefence);
        game.getVillage().getMap()[building.getX()][building.getY()]=airDefence;
    }
    private void initializeWizardTower(Game game,Cell building){
        WizardTower wizardTower= new WizardTower(game.getVillage().getWizardTowers().size(),building.getLevel());
        wizardTower.setX(building.getX());
        wizardTower.setY(building.getY());
        game.getVillage().getWizardTowers().add(wizardTower);
        game.getVillage().getMap()[building.getX()][building.getY()]=wizardTower;
    }
}
