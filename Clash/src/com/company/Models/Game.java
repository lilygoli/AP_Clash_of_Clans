package com.company.Models;

import com.company.Exception.*;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Cell;
import com.company.Models.Towers.Defences.AirDefence;
import com.company.Models.Towers.Defences.ArcherTower;
import com.company.Models.Towers.Defences.Cannon;
import com.company.Models.Towers.Defences.WizardTower;
import com.company.Models.Towers.Buildings.Storage;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.MainMenuUI;
import com.company.UIs.UIConstants;
import com.company.View.View;

import java.util.ArrayList;

public class Game {
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
        troops = null;
        village = new Village();
    }


    public void setAttackedVillage(Game attackedVillage) {
        this.attackedVillage = attackedVillage;
    }



    public ArrayList<Soldier> getTroops() {
        return troops;
    }

    public void setUnderAttackOrDefense(boolean underAttackOrDefense) {
        isUnderAttackOrDefense = underAttackOrDefense;
    }

    public boolean isUnderAttackOrDefense() {
        return isUnderAttackOrDefense;
    }

    public ArrayList<Game> getAllAttackedVillages() {
        return allAttackedVillages;
    }

    public void showResources() {
        View.show(village.showSourcesInfo());
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

    public String showAttackMenu() {
        StringBuilder finalString = new StringBuilder();
        finalString.append("1. Load Map\n");
        int counter = 2;
        for (Game game : GameCenter.getGames()) {
            finalString.append(counter).append(". ").append(game.playerName).append("\n");
            counter++;
        }
        finalString.append(counter).append(". Back\n");
        return finalString.toString();
    }

    public void showBuildings() {
        ArrayList<Cell> buildings = new ArrayList<>();
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (!cell.getClass().getSimpleName().equals("Grass") && !cell.getUnderConstructionStatus()) {
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

    private void rebuild() {
        for (Cell[] cells : village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().getSimpleName().equals("MainBuilding")) {
                    cell.setStrength(Config.getDictionary().get(cell.getClass().getSimpleName() + "_STRENGTH") + cell.getLevel() * 500);
                }
                if (!cell.getClass().getSimpleName().equals("Grass") && cell.isRuined()) {
                    cell.setRuined(false);
                }

                cell.setStrength(Config.getDictionary().get(cell.getClass().getSimpleName() + "_STRENGTH"));
            }
        }
    }

    public String statusResourcesInWar() {
        return "Gold Achieved : " + village.getGainedResource().getGold() + "\n" +
                "Elixir Achieved : " + village.getGainedResource().getElixir() + "\n" +
                "Gold Remained In Map : " + (attackedVillage.village.getResource().getGold()) + "\n" +
                "Elixir Remained In Map : " + (attackedVillage.village.getResource().getElixir()) + "\n";
    }

    public String statusUnit(String unitType) {
        StringBuilder finalString = new StringBuilder();
        for (Soldier troop : troops) {
            if (troop.getX() != -1 && troop.getY() != -1) {
                if (troop.getClass().getSimpleName().equals(unitType)) {
                    finalString.append(unitType).append(" level= ").append(troop.getLevel()).append(" in(").append(troop.getX()+1).append(",").append(troop.getY()+1).append(") with health").append(troop.getHealth()).append("\n");
                }
            }
        }

        return finalString.toString().trim();
    }

    public String statusUnits() {
        StringBuilder finalString = new StringBuilder();
        for (Soldier soldier : Soldier.getSoldierSubClasses()) {
            finalString.append(statusUnit(soldier.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString().trim();
    }

    public String statusTower(String towerType) {
        StringBuilder finalString = new StringBuilder();
        for (Cell[] cells : attackedVillage.village.getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().getSimpleName().equals(towerType) && !cell.isRuined()) {
                    finalString.append(cell.getClass().getSimpleName()).append(" level= ").append(cell.getLevel()).append(" in(").append(cell.getX()+1).append(",").append(cell.getY()+1).append(") with health ").append(cell.getStrength()).append("\n");
                    if (cell.getClass().getSimpleName().equals("MainBuilding"))
                        return finalString.toString();
                }
            }
        }
        return finalString.toString().trim();
    }

    public String statusTowers() {
        StringBuilder finalString = new StringBuilder();
        for (Cell cell : Cell.getCellKinds()) {
            finalString.append(statusTower(cell.getClass().getSimpleName())).append("\n");
        }
        return finalString.toString().trim();
    }

    public String statusAll() {
        return statusResourcesInWar()+"\n"+ statusTowers()+"\n" + statusUnits();
    }

    private void passTurnInWarMode() throws NotInWarException {
        if (this.attackedVillage == null) {
            throw new NotInWarException();
        }
        //Defender Map part
        for (Cannon cannon : this.attackedVillage.getVillage().getCannons()) {
            if (cannon.getUnderConstructionStatus() || cannon.isRuined()) {
                continue;
            }
            cannon.findAndShootUnit(this.troops);
        }
        for (ArcherTower archerTower : this.attackedVillage.getVillage().getArcherTowers()) {
            if (archerTower.getUnderConstructionStatus() || archerTower.isRuined()) {
                continue;
            }
            archerTower.findAndShootUnit(this.troops);
        }
        for (AirDefence airDefence : this.attackedVillage.getVillage().getAirDefences()) {
            if (airDefence.getUnderConstructionStatus() || airDefence.isRuined()) {
                continue;
            }
            airDefence.findAndShootUnit(this.troops);
        }
        for (WizardTower wizardTower : this.attackedVillage.getVillage().getWizardTowers()) {
            if (wizardTower.getUnderConstructionStatus() || wizardTower.isRuined()) {
                continue;
            }
            wizardTower.findAndShootUnit(this.troops);
        }
        //Attacker Soldiers part
        for (int i = 0; i < Config.getDictionary().get("KMM"); i++) {
            for (Soldier soldier : troops) {
                if (soldier.getX() == -1 && soldier.getY() == -1) {
                    continue;
                }
                System.out.println(soldier.getClass().getSimpleName() + " " + soldier.getDamage());
                soldier.attackTarget(this.getVillage(), this.attackedVillage.getVillage());
            }
            try {
                Thread.sleep(UIConstants.DELTA_T / Config.getDictionary().get("KMM"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        for (Soldier troop : troops) {
//            troop.setX((double)Math.round(troop.getX()));
//            troop.setY((double)Math.round(troop.getY()));
//        }
    }

    public void healAfterWar() {
        for (Soldier soldier : troops
                ) {
            soldier.setX(-1);
            soldier.setY(-1);
        }
        healSoldiers();
        attackedVillage.rebuild();

        Resource resource = new Resource(village.getGainedResource().getGold() + village.getResource().getGold(), village.getGainedResource().getElixir() + village.getResource().getElixir());
        village.setResource(resource);
    }

    private void healSoldiers() {
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
    public void passTurn() {
        if (isUnderAttackOrDefense) {
            try {
                passTurnInWarMode();
            } catch (NotInWarException e) {
                //something went wrong
            }
        } else {
            passTurnInNormalMode();
        }
    }



    public void putUnit(String unitType, int amount, int x, int y) throws MoreThanLimitSoldiersException, InvalidPlaceForSoldiersException, NotEnoughSoldierInTroopsException {
        ArrayList<Soldier> specialSoldierTypeInTroops=new ArrayList<>();
        for (Soldier soldier : troops) {
            if(soldier.getClass().getSimpleName().equalsIgnoreCase(unitType.trim().replace(" ",""))){
                if(soldier.getX()==-1 && soldier.getY()==-1) {
                    specialSoldierTypeInTroops.add(soldier);
                    if (specialSoldierTypeInTroops.size() == amount){
                        break;
                    }
                }
            }
        }
        if(amount>specialSoldierTypeInTroops.size()){
            throw new NotEnoughSoldierInTroopsException();
        }
        if (amount > 5) {
            throw new MoreThanLimitSoldiersException();
        }
        if((x<29 && x>0) && (y<29 && y>0)){
            throw new InvalidPlaceForSoldiersException();
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
        int number = 0;
        if (sameSoldiersNumber + amount <= 5) {
            for (Soldier specialSoldierTypeInTroop : specialSoldierTypeInTroops) {
                number++;
                specialSoldierTypeInTroop.setX(x);
                specialSoldierTypeInTroop.setY(y);
                if (number == amount) {
                    break;
                }
            }
        }else {
            throw new InvalidPlaceForSoldiersException();
        }

    }

    private void passTurnInNormalMode() {
        time++;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (!village.getMap()[j][i].getClass().getSimpleName().equals("Grass")) {
                    if (village.getMap()[j][i].getUnderConstructionStatus()) {
                        village.getMap()[j][i].setTimeLeftOfConstruction(village.getMap()[j][i].getTimeLeftOfConstruction()- 1);
                        if (village.getMap()[j][i].getTimeLeftOfConstruction() == 0) {
                            village.getMap()[j][i].setUnderConstructionStatus(false);
                            village.getMap()[j][i].getWorkingBuilder().setOccupationState(false);
                        }
                    }
                }
            }
        }
        for (Barrack barrack : village.getBarracks()) {
            barrack.transferToCamp(village.getCamps());
        }
        ArrayList<Storage> allElixirStorage = new ArrayList<>(village.getElixirStorages());
        for (ElixirMine elixirMine : village.getElixirMines()) {
            if(!elixirMine.getUnderConstructionStatus())
                elixirMine.addToMine(allElixirStorage);
        }
        ArrayList<Storage> allGoldStorage = new ArrayList<>(village.getGoldStorages());
        for (GoldMine goldMine : village.getGoldMines()) {
            if(!goldMine.getUnderConstructionStatus())
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

    public void selectUnit(String unitType) throws NoSuchSoldierInCampException {
        for (Camp camp : village.getCamps()) {
            for (Soldier soldier : camp.getSoldiers()) {
                if (soldier.getClass().getSimpleName().equals(unitType)) {
                    if (troops == null) {
                        troops = new ArrayList<>();
                    }
                    troops.add(soldier);
                    camp.removeSoldier(soldier);
                    return;
                }
            }
        }
        throw new NoSuchSoldierInCampException();
    }

    public void setTroops(ArrayList<Soldier> troops) {
        this.troops=troops;
    }
}


