package com.company.Models.Buildings;

import com.company.Exception.NotEnoughResourcesException;
import com.company.Models.Soldier.Soldier;
import com.company.View.View;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Barracks extends Building {
    private ArrayList<HashMap<Soldier, Integer>> underConstructionSoldiers = new ArrayList<HashMap<Soldier, Integer>>();
    private int timeDecreasedToMakeASoldier = 0;
    private int soldierBuildLevel = 0;
    private boolean canBeUpgraded = true;

    public Barracks(int number) {
        super(number, 0);
    }

    @Override
    public void upgrade() { // the comparison with mainBuilding level should be handled in Game
        if (canBeUpgraded) {
            if (timeDecreasedToMakeASoldier < 45) {
                timeDecreasedToMakeASoldier++;//in soldiers constructor it should be handled if it gets negative
            } else {
                canBeUpgraded = false;
            }
            if (canBeUpgraded) {
                soldierBuildLevel++;
            }
        }
    }

    public void showMenu() {
        String infoMenu = "1. Info\n2. Build Soldiers\n3. Status\n4. back";
        View.show(infoMenu);
    }

    public void buildSoldier(int number, String name, int time, HashMap<String, Integer> availableSoldiers) throws
            NotEnoughResourcesException {

        for (Soldier soldier : Soldier.getSoldierSubClasses()
                ) {
            if (soldier.getClass().toString().split(" ")[1].equals(name.trim())) {
                if (availableSoldiers.get(name) >= number) {
                    for (int i = 0; i < number; i++) {
                        Soldier newSoldier = null;
                        try {
                            newSoldier = soldier.getClass().getDeclaredConstructor().newInstance();//depends on the soldier Constructor
                            //build duration and level should be set by deceasedBuildingDuration and SoldierBuildLevel
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        HashMap<Soldier, Integer> startingTimeOfBuildingSoldier = new HashMap<Soldier, Integer>();
                        startingTimeOfBuildingSoldier.put(soldier, time);
                        underConstructionSoldiers.add(startingTimeOfBuildingSoldier);
                    }
                } else {
                    throw new NotEnoughResourcesException();
                }
            }
        }
    }

    public void transferToCamp(int time, ArrayList<Camp> camps) { //should be called in each turn
        int index = 0;
        for (HashMap<Soldier, Integer> soldierInitialTime : underConstructionSoldiers
                ) {
            Soldier soldier = (Soldier) soldierInitialTime.keySet().toArray()[0];
            if (time == soldierInitialTime.get(soldier) + soldier.getBuildDuration()) {
                for (Camp camp : camps
                        ) {
                    if (camp.getSoldiers().size() < camp.getCapacity()) {
                        camp.getSoldiers().add(soldier);
                        break;
                    }
                }
                underConstructionSoldiers.remove(index);
            }
            index++;
        }
    }

    public HashMap<String, Integer> determineAvailableSoldiers(int gold, int elixir) {
        HashMap<String, Integer> availableSoldiers = new HashMap<String, Integer>(); //number of soldiers we cannot build is 0
        for (Soldier soldier : Soldier.getSoldierSubClasses()
                ) {
            if (soldier.getSoldierUnlockLevel() <= getLevel()) {
                int number = 0;
                if (gold / soldier.getSoldierGoldCost() < elixir / soldier.getSoldierElixirCost()) {
                    number = gold / soldier.getSoldierGoldCost();
                } else {
                    number = elixir / soldier.getSoldierElixirCost();
                }
                availableSoldiers.put(soldier.getClass().toString().split(" ")[1], number);
            } else {
                availableSoldiers.put(soldier.getClass().toString().split(" ")[1], 0);
            }
        }
        return availableSoldiers;
    }
}
