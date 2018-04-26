package com.company.Models;

import com.company.Models.Buildings.Grass;
import com.company.Models.Buildings.MainBuilding;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;

public class Game {
    private Resource gainedResource;
    private String playerName="";
    private Village village=new Village();
    private int time=0;
    private boolean isUnderAttackOrDefense=false;
    private Game attackedVillage=null;
    private ArrayList<Game> allAttackedVillages=new ArrayList<Game>();
    private static String whereIAm="You are in village";
    private ArrayList<Soldier> troops=null;
    {
        gainedResource.setElixir(0);
        gainedResource.setGold(0);
    }
    public void setAttackedVillage(Game attackedVillage) {
        this.attackedVillage = attackedVillage;
    }

    public void setGainedResource(Resource gainedResource) {
        this.gainedResource = gainedResource;
    }

    public Resource getGainedResource() {
        return gainedResource;
    }

    public void setTroops(ArrayList<Soldier> troops) {
        this.troops = troops;
    }

    public void setUnderAttackOrDefense(boolean underAttackOrDefense) {
        isUnderAttackOrDefense = underAttackOrDefense;
    }



    public boolean isUnderAttackOrDefense() {
        return isUnderAttackOrDefense;
    }

    public void setAllAttackedVillages(ArrayList<Game> allAttackedVillages) {
        this.allAttackedVillages = allAttackedVillages;
    }

    public ArrayList<Game> getAllAttackedVillages() {
        return allAttackedVillages;
    }


    public void showResources(){
        village.showSourcesInfo();
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setAttackStatus(boolean isUnderAttack){
        this.isUnderAttackOrDefense =isUnderAttack;
    }
    public boolean getAttackStatus(){
        return isUnderAttackOrDefense;
    }
    public String showAttackMenu(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("1.Load Map\n");
        int counter = 2;
        for (Game game : GameCenter.getGames()) {
            finalString.append(counter).append(".").append(game.playerName).append("\n");
            counter++;
        }
        finalString.append(counter).append(".Back\n");
        return finalString.toString();
    }

    public void showBuildings() {
        ArrayList<Cell> buildings = new ArrayList<>();
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (!cell.getClass().isInstance(Grass.class)) {
                    buildings.add(cell);
                }
            }
            Cell.sortTowers(buildings);
            for (Cell building : buildings) {
                View.show(building.getName() + building.getNumber());
            }
        }
    }

    public static String getWhereIAm() {
        return whereIAm;
    }

    public static void setWhereIAm(String whereIam) {
        Game.whereIAm = whereIam;
    }

    public String showEnemyMapInfo(Village enemyVillage) {
        Game.setWhereIAm("You are in enemy map menu");
        StringBuilder result= new StringBuilder();
        result.append("Gold: ").append(enemyVillage.getResource().getGold()).append("\nElixir: ").append(enemyVillage.getResource().getElixir());
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

    public String showEnemyMapMenu(){//whereIam should be changed back to you are in village where this is called
        whereIAm="EnemyMapMenu";
        return "1. Map Info\n2. Attack map\n3. Back";
    }
    public void showWhereIAm(){
        View.show(whereIAm);
    }

    public void rebuild() {
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().isInstance(MainBuilding.class)) {
                    cell.setStrength(Config.getDictionary().get(this.getClass().getSimpleName() + "_STRENGTH") + cell.getLevel() * 500);
                }
                if (!cell.getClass().isInstance(Grass.class) && cell.isRuined()) {
                    cell.setRuined(false);
                }
                cell.setStrength(Config.getDictionary().get(this.getClass().getSimpleName() + "_STRENGTH"));
            }
        }
    }

    public String statusResources(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("Gold Achieved : " + gainedResource.getGold() + "\n");
        finalString.append("Elixir Achieved : " + gainedResource.getElixir() + "\n");
        finalString.append("Gold Remained In Map : " + (attackedVillage.village.getResource().getGold() - gainedResource.getGold()) + "\n");
        finalString.append("Elixir Remained In Map : " + (attackedVillage.village.getResource().getElixir() - gainedResource.getElixir()) + "\n");
        return finalString.toString();
    }
    public String statusUnit(String unitType){
        StringBuilder finalString = new StringBuilder();
        for (Soldier troop : troops) {
            if (troop.getClass().getSimpleName().equals(unitType)){
                finalString.append(unitType + " level= " + troop.getLevel() + " in(" + troop.getX() + "," + troop.getY() + ") with health" + troop.getHealth() + "\n");
            }
        }
        return finalString.toString();
    }
    public String statusUnit(){
        StringBuilder finalString = new StringBuilder();
        for (Soldier soldier : Soldier.getSoldierSubClasses()) {
            finalString.append(statusUnit(soldier.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString();
    }

    public String statusTower(String towerType){
        StringBuilder finalString = new StringBuilder();
        for (Cell[] cells : attackedVillage.village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().getSimpleName().equals(towerType) && !cell.isRuined()){
                    finalString.append(towerType).append(" level= ").append(cell.getLevel()).append(" in(").append(cell.getX()).append(",").append(cell.getY()).append(") with health").append(cell.getStrength()).append("\n");
                }
            }
        }
        return finalString.toString();
    }

    // TODO: 4/23/2018 put unit
    public String statusTower(){
        StringBuilder finalString = new StringBuilder();
        for (Cell cell : Cell.getCellKinds()) {
            finalString.append(statusTower(cell.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString();
    }

    public String statusAll(){
        StringBuilder finalString = new StringBuilder();
        finalString.append(statusResources() + statusTower() + statusUnit());
        return finalString.toString();
    }


}
