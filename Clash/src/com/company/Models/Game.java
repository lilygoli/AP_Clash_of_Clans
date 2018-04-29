package com.company.Models;

import com.company.Exception.NotInWarException;
import com.company.Exception.InvalidPlaceForSoldiersException;
import com.company.Exception.MoreThanLimitSoldiersException;
import com.company.Models.Buildings.Camp;
import com.company.Models.Buildings.Grass;
import com.company.Models.Buildings.MainBuilding;
import com.company.Models.Defences.AirDefence;
import com.company.Models.Defences.ArcherTower;
import com.company.Models.Defences.Cannon;
import com.company.Models.Defences.WizardTower;
import com.company.Models.Buildings.Storage;
import com.company.Models.Buildings.*;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;

public class Game {
    private Resource gainedResource;
    private String playerName;
    private Village village;
    private int time;
    private boolean isUnderAttackOrDefense;
    private int timePassedInWar;

    public Game getAttackedVillage() {
        return attackedVillage;
    }

    private Game attackedVillage;
    private ArrayList<Game> allAttackedVillages;
    private static String whereIAm;
    private ArrayList<Soldier> troops;

    {
        playerName = "";
        time = 0;
        isUnderAttackOrDefense = false;
        timePassedInWar = 0;
        attackedVillage = null;
        allAttackedVillages = new ArrayList<Game>();
        whereIAm = "You are in village";
        gainedResource = new Resource(0 , 0);
        troops = null;
        village = new Village();
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


    public void showResources() {
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

    public void setAttackStatus(boolean isUnderAttack) {
        this.isUnderAttackOrDefense = isUnderAttack;
    }

    public boolean getAttackStatus() {
        return isUnderAttackOrDefense;
    }

    public String showAttackMenu() {
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
                if (!cell.getClass().getSimpleName().equals("Grass")) {
                    if (!buildings.contains(cell)) {
                        buildings.add(cell);
                    }
                }
            }
        }
        Cell.sortTowers(buildings);
        for (Cell building : buildings) {
            View.show(building.getName() + " " + building.getNumber());
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
        StringBuilder result = new StringBuilder();
        result.append("Gold: ").append(enemyVillage.getResource().getGold()).append("\nElixir: ").append(enemyVillage.getResource().getElixir());
        for (Cell cell : Cell.getCellKinds()
                ) {
            result.append("\n").append(cell.getName()).append(": ").append(getNumberOfTower(cell, enemyVillage));
        }
        return result.toString();
    }

    private int getNumberOfTower(Cell cell, Village enemyVillage) {
        int number = 0;
        switch (cell.getName()) {
            case "Camp":
                number = enemyVillage.getCamps().size();
                break;
            case "Barrack":
                number = enemyVillage.getBarracks().size();
                break;
            case "ElixirStorage":
                number = enemyVillage.getElixirStorages().size();
                break;
            case "GoldStorage":
                number = enemyVillage.getElixirStorages().size();
                break;
            case "ElixirMine":
                number = enemyVillage.getElixirMines().size();
                break;
            case "GoldMine":
                number = enemyVillage.getGoldMines().size();
                break;
            case "MainBuilding":
                number = 1;
                break;
            case "AirDefense":
                number = enemyVillage.getAirDefences().size();
                break;
            case "ArcherTower":
                number = enemyVillage.getArcherTowers().size();
                break;
            case "Cannon":
                number = enemyVillage.getCannons().size();
                break;
            case "WizardTower":
                number = enemyVillage.getWizardTowers().size();
                break;
        }
        return number;
    }

    public String showEnemyMapMenu() {//whereIam should be changed back to you are in village where this is called
        whereIAm = "EnemyMapMenu";
        return "1. Map Info\n2. Attack map\n3. Back";
    }

    public void showWhereIAm() {
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

    public String statusResources() {
        return "Gold Achieved : " + gainedResource.getGold() + "\n" +
                "Elixir Achieved : " + gainedResource.getElixir() + "\n" +
                "Gold Remained In Map : " + (attackedVillage.village.getResource().getGold() - gainedResource.getGold()) + "\n" +
                "Elixir Remained In Map : " + (attackedVillage.village.getResource().getElixir() - gainedResource.getElixir()) + "\n";
    }

    public String statusUnit(String unitType) {
        StringBuilder finalString = new StringBuilder();
        for (Soldier troop : troops) {
            if (troop.getClass().getSimpleName().equals(unitType)) {
                finalString.append(unitType).append(" level= ").append(troop.getLevel()).append(" in(").append(troop.getX()).append(",").append(troop.getY()).append(") with health").append(troop.getHealth()).append("\n");
            }
        }
        return finalString.toString();
    }

    public String statusUnit() {
        StringBuilder finalString = new StringBuilder();
        for (Soldier soldier : Soldier.getSoldierSubClasses()) {
            finalString.append(statusUnit(soldier.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString();
    }

    public String statusTower(String towerType) {
        StringBuilder finalString = new StringBuilder();
        for (Cell[] cells : attackedVillage.village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().getSimpleName().equals(towerType) && !cell.isRuined()) {
                    finalString.append(towerType).append(" level= ").append(cell.getLevel()).append(" in(").append(cell.getX()).append(",").append(cell.getY()).append(") with health").append(cell.getStrength()).append("\n");
                }
            }
        }
        return finalString.toString();
    }

    public String statusTowers() {
        StringBuilder finalString = new StringBuilder();
        for (Cell cell : Cell.getCellKinds()) {
            finalString.append(statusTower(cell.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString();
    }

    public String statusAll() {
        return statusResources() + statusTowers() + statusUnit();
    }

    public void passTurnInWarMode() throws NotInWarException {
        if (this.attackedVillage == null) {
            throw new NotInWarException();
        }
        //Defender Map part
        for (Cannon cannon : this.attackedVillage.getVillage().getCannons()) {
            if (cannon.getUnderConstructionStatus()) {
                continue;
            }
            cannon.findAndShootUnit(this.troops);
        }
        for (ArcherTower archerTower : this.attackedVillage.getVillage().getArcherTowers()) {
            if (archerTower.getUnderConstructionStatus()) {
                continue;
            }
            archerTower.findAndShootUnit(this.troops);
        }
        for (AirDefence airDefence : this.attackedVillage.getVillage().getAirDefences()) {
            if (airDefence.getUnderConstructionStatus()) {
                continue;
            }
            airDefence.findAndShootUnit(this.troops);
        }
        for (WizardTower wizardTower : this.attackedVillage.getVillage().getWizardTowers()) {
            if (wizardTower.getUnderConstructionStatus()) {
                continue;
            }
            wizardTower.findAndShootUnit(this.troops);
        }
        //Attacker Soldiers part
        for (Soldier soldier : troops) {
            soldier.attackTarget(this.attackedVillage.getVillage()); // TODO: 4/27/18 باید چند بار کال شه این تابع تو هر ترن
        }
    }

    public void healAfterWar() {
        for (Soldier soldier : troops
                ) {
            soldier.setX(-1);
            soldier.setY(-1);
        }
        healSoldiers();
        attackedVillage.rebuild();
    }

    public void healSoldiers() {
        for (Soldier troop : troops) {
            troop.heal();
        }
        for (Soldier troop : troops) {
            for (Camp camp : village.getCamps()) {
                if (camp.getSoldiers().size() < camp.getCapacity()) {
                    camp.getSoldiers().add(troop);
                }
            }
        }
        troops.clear();
    }

    public void startAttack() {
    }

    public void passTurn(){
        if(isUnderAttackOrDefense){
            try {
                passTurnInWarMode();
            } catch (NotInWarException e) {
                //something went wrong
            }
        }else {
            passTurnInNormalMode();
        }
    }
    // TODO: 4/29/2018 select unit

    public void putUnit(String unitType, int amount, int x, int y) throws MoreThanLimitSoldiersException, InvalidPlaceForSoldiersException {
        if (amount > 5) {
            throw new MoreThanLimitSoldiersException();
        }
        int sameSoldiersNumber = 0;
        for (Soldier soldier : troops
                ) {
            if (soldier.getY() != -1 && soldier.getX() != -1 && soldier.getX() == x && soldier.getY() == y) {
                if (soldier.getClass().getSimpleName().equalsIgnoreCase(unitType.replace(" ", ""))) {
                    sameSoldiersNumber++;
                } else {
                    throw new InvalidPlaceForSoldiersException();
                }
            }
        }
        int number=0;
        if (sameSoldiersNumber + amount <= 5) {
            for (Soldier soldier : troops
                    ) {
                if(soldier.getX()==-1 && soldier.getY()==-1 && soldier.getClass().getSimpleName().equalsIgnoreCase(unitType.replace(" ", ""))){
                    number++;
                    soldier.setX(x);
                    soldier.setY(y);
                    if(number==amount){
                        break;
                    }
                }
            }
        }

    }

    private void passTurnInNormalMode(){
        time++;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if(village.getMap()[j][i].getUnderConstructionStatus()){
                    village.getMap()[j][i].setTimeLeftOfConstruction(village.getMap()[j][i].getTimeTillConstruction()-1);
                    if(village.getMap()[j][i].getTimeLeftOfConstruction()==0){
                        village.getMap()[j][i].setUnderConstructionStatus(false);
                        village.getMap()[j][i].getWorkingBuilder().setOccupationState(false);
                    }
                }
            }
        }
        for (Barrack barrack : village.getBarracks()) {
            barrack.transferToCamp(village.getCamps());
        }
        ArrayList<Storage> allElixirStorage = new ArrayList<>(village.getElixirStorages());
        for (ElixirMine elixirMine : village.getElixirMines()) {
            elixirMine.addToMine(allElixirStorage);
        }
        ArrayList<Storage> allGoldStorage = new ArrayList<>(village.getGoldStorages());
        for (GoldMine goldMine : village.getGoldMines()) {
            goldMine.addToMine(allGoldStorage);
        }

    }

    public boolean isWarFinished() {

        int flag = 0;
        ArrayList<Storage> allStorage = new ArrayList<>(village.getElixirStorages());
        allStorage.addAll(village.getGoldStorages());
        for (Storage storage : allStorage
                ) {
            if (!storage.isFull()) {
                flag = 1;
            }
        }
        return timePassedInWar >= 10000 || troops.size() == 0 || flag == 0 || (attackedVillage.getVillage().getResource().getGold() == 0 && attackedVillage.getVillage().getResource().getElixir() == 0);
    }

}
