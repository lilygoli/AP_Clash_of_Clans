package com.company.Models;

import com.company.Exception.BusyCellException;
import com.company.Exception.MarginalTowerException;
import com.company.Exception.NotEnoughFreeBuildersException;
import com.company.Models.Soldiers.Guardian;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Cell;
import com.company.Models.Towers.Defences.*;
import com.company.Models.Soldiers.Soldier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Village implements Serializable{
    private Cell[][] map;
    private int score = 0;
    private ArrayList<ArcherTower> archerTowers = new ArrayList<ArcherTower>();
    private ArrayList<Cannon> cannons = new ArrayList<Cannon>();
    private ArrayList<AirDefence> airDefences = new ArrayList<AirDefence>();
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<WizardTower> wizardTowers = new ArrayList<WizardTower>();
    private ArrayList<Trap> traps = new ArrayList<Trap>();
    // TODO: 4/19/2018 add guardian giant arrayList and class
    private ArrayList<GoldMine> goldMines = new ArrayList<GoldMine>();
    private ArrayList<ElixirMine> elixirMines = new ArrayList<ElixirMine>();
    private ArrayList<GoldStorage> goldStorages = new ArrayList<GoldStorage>();
    private ArrayList<ElixirStorage> elixirStorages = new ArrayList<ElixirStorage>();
    private ArrayList<GuardianGiant> guardianGiants= new ArrayList<>();
    private MainBuilding mainBuilding;
    private ArrayList<Barrack> barracks = new ArrayList<Barrack>();
    private ArrayList<Camp> camps = new ArrayList<Camp>();
    private Resource gainedResource = new Resource(0, 0);

    {
        map = new Cell[30][30];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                map[j][i] = new Grass();
            }
        }
        mainBuilding = new MainBuilding(0);

        map[14][14] = mainBuilding;
        map[14][15] = mainBuilding;
        map[15][14] = mainBuilding;
        map[15][15] = mainBuilding;

        goldStorages.add(new GoldStorage(1, 0));
        elixirStorages.add(new ElixirStorage(1, 0));
        goldStorages.get(0).setResource(2000);
        elixirStorages.get(0).setResource(1000);
        Random random = new Random();
        int XGoldStorage;
        int YGoldStorage;
        do {
            XGoldStorage = random.nextInt(28) + 1;
            YGoldStorage = random.nextInt(28) + 1;
        }while ((13<XGoldStorage && XGoldStorage<16) || (13<YGoldStorage && YGoldStorage<16));
        int XElixirStorage;
        int YElixirStorage;
        do {
             YElixirStorage= random.nextInt(28) + 1;
             XElixirStorage= random.nextInt(28) + 1;
        }while ((13<XElixirStorage && XElixirStorage<16) || (13<YElixirStorage && YElixirStorage<16));
        map[XGoldStorage][YGoldStorage] = goldStorages.get(0);
        map[XElixirStorage][YElixirStorage] = elixirStorages.get(0);
        goldStorages.get(0).setX(XGoldStorage);
        goldStorages.get(0).setY(YGoldStorage);
        elixirStorages.get(0).setX(XElixirStorage);
        elixirStorages.get(0).setY(YElixirStorage);
        goldStorages.get(0).setResource(Config.getDictionary().get("STARTING_GOLD"));// TODO: 4/29/2018 what int resouce should be
        elixirStorages.get(0).setResource(Config.getDictionary().get("STARTING_ELIXIR"));
    }

    public void setGainedResource(Resource gainedResource) {
        this.gainedResource = gainedResource;
    }

    public Resource getGainedResource() {
        return this.gainedResource;
    }

    public ArrayList<GuardianGiant> getGuardianGiants() {
        return guardianGiants;
    }

    public void setGuardianGiants(ArrayList<GuardianGiant> guardianGiants) {
        this.guardianGiants = guardianGiants;
    }

    public void setResource(Resource resource) {
        int excessGold = resource.getGold() - this.getResource().getGold();
        int excessElixir = resource.getElixir() - this.getResource().getElixir();
        setGold(excessGold);
        setElixir(excessElixir);
    }

    private void setGold(int excessGold) {
        if (excessGold > 0) {
            for (GoldStorage goldStorage : goldStorages) {
                if (goldStorage.getCapacity() - goldStorage.getResource() < excessGold) {
                    excessGold -= goldStorage.getCapacity() - goldStorage.getResource();
                    goldStorage.setResource(goldStorage.getCapacity());
                }else{
                    goldStorage.setResource(goldStorage.getResource()+excessGold);
                    break;
                }
            }
        }else{
            for (GoldStorage goldStorage : goldStorages) {
                if(goldStorage.getResource()> -(excessGold)){
                    goldStorage.setResource(goldStorage.getResource()+excessGold);
                    break;
                }else{
                    excessGold+=goldStorage.getResource();
                    goldStorage.setResource(0);
                }
            }
        }
    }

    private void setElixir(int excessElixir) {
        if (excessElixir > 0) {
            for (ElixirStorage elixirStorage : elixirStorages) {
                if (elixirStorage.getCapacity() - elixirStorage.getResource() < excessElixir) {
                    excessElixir -= elixirStorage.getCapacity() - elixirStorage.getResource();
                    elixirStorage.setResource(elixirStorage.getCapacity());
                }else{
                    elixirStorage.setResource(elixirStorage.getResource()+excessElixir);
                    break;
                }
            }
        }else{
            for (ElixirStorage elixirStorage : elixirStorages) {
                if(elixirStorage.getResource()> -(excessElixir)){
                    elixirStorage.setResource(elixirStorage.getResource()+excessElixir);
                    break;
                }else{
                    excessElixir+=elixirStorage.getResource();
                    elixirStorage.setResource(0);
                }
            }
        }
    }
    public Resource getResource() {
        int gold = 0, elixir = 0;
        for (GoldStorage goldStorage : getGoldStorages()) {
            if(!goldStorage.isRuined())
                gold += goldStorage.getResource();
        }
        for (ElixirStorage elixirStorage : getElixirStorages()) {
            if(!elixirStorage.isRuined())
                elixir += elixirStorage.getResource();
        }
        return new Resource(gold, elixir);
    }

    public void setGoldStorages(ArrayList<GoldStorage> goldStorages) {
        this.goldStorages = goldStorages;
    }

    public void setElixirStorages(ArrayList<ElixirStorage> elixirStorages) {
        this.elixirStorages = elixirStorages;
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
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

    public void buildTower(Cell tower) throws BusyCellException, MarginalTowerException {
        if (tower.getX() <= 0 || tower.getX() >= 29 || tower.getY() <= 0 || tower.getY() >= 29) {
            throw new MarginalTowerException();
        }
        if (map[tower.getX()][tower.getY()].getClass().getSimpleName().equals("Grass")) {
            map[tower.getX()][tower.getY()] = tower;
            tower.setUnderConstructionStatus(true);
            tower.setTimeLeftOfConstruction(Config.getDictionary().get(tower.getClass().getSimpleName() + "_BUILD_DURATION"));
            switch (tower.getName()) {
                case "Camp":
                    this.getCamps().add((Camp) tower);
                    tower.setNumber(getCamps().size());
                    break;
                case "Barrack":
                    this.getBarracks().add((Barrack) tower);
                    tower.setNumber(getBarracks().size());
                    break;
                case "ElixirStorage":
                    this.getElixirStorages().add((ElixirStorage) tower);
                    tower.setNumber(getElixirStorages().size());
                    break;
                case "GoldStorage":
                    this.getGoldStorages().add((GoldStorage) tower);
                    tower.setNumber(getGoldStorages().size());
                    break;
                case "ElixirMine":
                    this.getElixirMines().add((ElixirMine) tower);
                    tower.setNumber(getElixirMines().size());
                    break;
                case "GoldMine":
                    this.getGoldMines().add((GoldMine) tower);
                    tower.setNumber(getGoldMines().size());
                    break;
                case "AirDefence":
                    this.getAirDefences().add((AirDefence) tower);
                    tower.setNumber(getAirDefences().size());
                    break;
                case "ArcherTower":
                    this.getArcherTowers().add((ArcherTower) tower);
                    tower.setNumber(getArcherTowers().size());
                    break;
                case "Cannon":
                    this.getCannons().add((Cannon) tower);
                    tower.setNumber(getCannons().size());
                    break;
                case "WizardTower":
                    this.getWizardTowers().add((WizardTower) tower);
                    tower.setNumber(getWizardTowers().size());
                    break;
                case "GuardianGiant":
                    this.getGuardianGiants().add((GuardianGiant) tower);
                    tower.setNumber(getGuardianGiants().size());
            }

        } else {
            throw new BusyCellException();
        }
    }

    public Builder findFreeBuilder() throws NotEnoughFreeBuildersException {
        int numberOfFreeBuilders = 0;
        Builder builderToConstruct = null;
        for (Builder builder : this.getMainBuilding().getBuilders()) {
            if (!builder.getOccupationState()) {
                builderToConstruct = builder;
                numberOfFreeBuilders++;
            }
        }
        if (numberOfFreeBuilders == 0) {
            throw new NotEnoughFreeBuildersException();
        }
        return builderToConstruct;
    }

    public String showTownHallStatus() {
        StringBuilder status = new StringBuilder();
        ArrayList<Cell> underConstructionTowers = new ArrayList<>();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.getUnderConstructionStatus()) {
                    underConstructionTowers.add(cell);
                }
            }
        }
        Cell.sortTowers(underConstructionTowers);
        for (Cell underConstructionTower : underConstructionTowers) {
            status.append(underConstructionTower.getName()).append(" will be built in\n").append(underConstructionTower.getTimeLeftOfConstruction()).append(" turns\n");
        }
        return status.toString().trim();
    }


    public String showBarracksStatus() {
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> allTime=new ArrayList<Integer>();
        for (Integer timeLeft : sortSoldiersByTimeLeft()
                ) {
            if(allTime.contains(timeLeft)){
                continue;
            }
            allTime.add(timeLeft);
            for (Barrack barrack : barracks
                    ) {
                for (HashMap<Soldier, Integer> soldierInitialTime : barrack.getUnderConstructionSoldiers()
                        ) {
                    for (Soldier soldier : soldierInitialTime.keySet()) {
                        if (soldier.getBuildDuration() - soldierInitialTime.get(soldier) == timeLeft) {
                            result.append(soldier.getClass().getSimpleName()).append(" Level ").append(soldier.getLevel()).append(" will be built in ").append(timeLeft.toString()).append("\n");
                        }
                    }
                }
            }

        }
        return result.toString().trim();
    }

    private ArrayList<Integer> sortSoldiersByTimeLeft() {
        ArrayList<Integer> turnsLeft = new ArrayList<Integer>();
        for (Barrack barrack : barracks
                ) {
            for (HashMap<Soldier, Integer> soldierInitialTime : barrack.getUnderConstructionSoldiers()
                    ) {
                for (Soldier soldier : soldierInitialTime.keySet()) {
                    turnsLeft.add(soldier.getBuildDuration() - soldierInitialTime.get(soldier));
                }
            }
        }
        turnsLeft.sort((a, b) -> a < b ? a : b);
        return turnsLeft;
    }

    public String showSourcesInfo() {
        String result = "";
        result = result + "Gold : " + getResource().getGold() + "\nElixir : " + getResource().getElixir() + "\nScore : " + score;
        return result;
    }
}
