package com.company.Models;

import com.company.Exception.*;
import com.company.Models.Soldiers.Healer;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Cell;
import com.company.Models.Towers.Defences.*;
import com.company.Models.Towers.Buildings.Storage;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.MainMenuUI;
import com.company.UIs.UIConstants;
import com.company.View.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Game implements Serializable {
    private String playerName;
    private Village village;
    private int time;
    private boolean isUnderAttackOrDefense;
    private int timePassedInWar;
    private ArrayList<Game> allAttackedVillages;
    private Game attackedVillage;
    private static String whereIAm;
    public Game getAttackedVillage() {
        return attackedVillage;
    }


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
            case "GuardianGiant":
                number=enemyVillage.getGuardianGiants().size();
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
        timePassedInWar++;
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
        for (Trap trap : this.attackedVillage.getVillage().getTraps()) {
            if (trap.getUnderConstructionStatus() || trap.isRuined()) {
                continue;
            }
            trap.findAndShootUnit(this.troops);
        }
        for (GuardianGiant guardianGiant : this.attackedVillage.getVillage().getGuardianGiants()) {
            if (guardianGiant.getUnderConstructionStatus() || guardianGiant.isRuined()){
                continue;
            }
            guardianGiant.findAndShootUnit(this.troops);
        }
        //Attacker Soldiers part
        for (int i = 0; i < Config.getDictionary().get("KMM"); i++) {
            for (int j = troops.size()-1; j > -1 ; j--) {
                if (troops.get(j).getX() == -1 && troops.get(j).getY() == -1) {
                    continue;
                }
                System.out.println(troops.get(j).getClass().getSimpleName() + " " + troops.get(j).getDamage());
                troops.get(j).attackTarget(this.getVillage(), this.attackedVillage.getVillage());
                if(troops.get(j).getClass().getSimpleName().equals("Healer")){
                    ((Healer)troops.get(j)).setTimeInWar(((Healer) troops.get(j)).getTimeInWar()+1);
                    if(((Healer) troops.get(j)).getTimeInWar()>500){
                        troops.get(j).setHealth(0);
                    }
                }
                if (troops.get(j).getHealth() <= 0){
                    troops.remove(troops.get(j));
                    troops.get(j).getImageView().setImage(null);
                }

            }
//            Iterator<Soldier> iterator = troops.iterator();
//            while(iterator.hasNext()){
//                Soldier soldier = iterator.next();
//                if (soldier.getX() == -1 && soldier.getY() == -1) {
//                    continue;
//                }
//                System.out.println(soldier.getClass().getSimpleName() + " " + soldier.getDamage());
//                soldier.attackTarget(this.getVillage(), this.attackedVillage.getVillage());
//                if(soldier.getClass().getSimpleName().equals("Healer")){
//                    ((Healer)soldier).setTimeInWar(((Healer) soldier).getTimeInWar()+1);
//                    if(((Healer) soldier).getTimeInWar()>500){
//                        soldier.setHealth(0);
//                    }
//                }
//                if (soldier.getHealth() <= 0){
//                    iterator.remove();
//                    soldier.getImageView().setImage(null);
//                }
//            }
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
        timePassedInWar=0;
        for (Soldier soldier : troops
                ) {
            soldier.setX(-1);
            soldier.setY(-1);
            if(soldier.getClass().getSimpleName().equals("Healer")){
                ((Healer)soldier).setTimeInWar(0);
            }
        }
        healSoldiers();
        attackedVillage.rebuild();
        attackedVillage.getVillage().setResource(new Resource(attackedVillage.getVillage().getResource().getGold()-village.getGainedResource().getGold(),attackedVillage.getVillage().getResource().getElixir()-village.getGainedResource().getElixir()));
        Resource resource = new Resource(village.getGainedResource().getGold() + village.getResource().getGold(), village.getGainedResource().getElixir() + village.getResource().getElixir());
        village.setResource(resource);
    }

    private void healSoldiers() {
        for (Soldier troop : troops) {
            troop.heal();
        }
        for (Camp camp : village.getCamps()) {
            camp.getSoldiers().clear();
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

    private void passTurnInNormalMode() {
        time++;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                Cell cell = village.getMap()[j][i];
                if (!cell.getClass().getSimpleName().equals("Grass")) {
                    if (cell.getUnderConstructionStatus()) {
                        cell.setTimeLeftOfConstruction(cell.getTimeLeftOfConstruction()- 1);
                        if (cell.getTimeLeftOfConstruction() == 0) {
                            cell.setUnderConstructionStatus(false);
                            cell.getWorkingBuilder().setOccupationState(false);
                        }
                    }
                    //upgrade tobe recent
                    if(cell.getTimeLeftOfUpgrade()>0) {
                        cell.setTimeLeftOfUpgrade(cell.getTimeLeftOfUpgrade() - 1);
                    }
                    if(cell.getTimeLeftOfUpgrade()==0){
                        cell.setTimeLeftOfUpgrade(-1);
                        cell.upgrade(); //recent
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
        return  timePassedInWar>10000 || troops.size() == 0 || flag==0|| (attackedVillage.getVillage().getResource().getGold() == 0 && attackedVillage.getVillage().getResource().getElixir() == 0);
    }



    public void setTroops(ArrayList<Soldier> troops) {
        this.troops=troops;
    }
}


