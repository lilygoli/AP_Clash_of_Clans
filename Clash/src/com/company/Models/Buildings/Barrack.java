package com.company.Models.Buildings;

import com.company.Exception.NotEnoughResourcesException;
import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Barrack extends Building {
    private ArrayList<HashMap<Soldier, Integer>> underConstructionSoldiers = new ArrayList<HashMap<Soldier, Integer>>();
    private int timeDecreasedToMakeASoldier = 0;
    private int soldierBuildLevel = 0;
    private boolean canBeUpgraded = true;

    public Barrack(int number,int level) {
        super(number, level);
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
        Game.setWhereIAm("You are in Barracks Menu");
        String infoMenu = "1. Info\n2. Build Soldiers\n3. Status\n4. back";
        View.show(infoMenu);
    }

    public void buildSoldier(int number, String name, HashMap<String, Integer> availableSoldiers) throws
            NotEnoughResourcesException {

        for (Soldier soldier : Soldier.getSoldierSubClasses()
                ) {
            if (soldier.getClass().toString().split(" ")[1].equals(name.trim())) {
                if (availableSoldiers.get(name) >= number) {
                    for (int i = 0; i < number; i++) {
                        Soldier newSoldier = null;
                        try {
                            newSoldier = soldier.getClass().getDeclaredConstructor(int.class).newInstance(timeDecreasedToMakeASoldier);//depends on the soldier Constructor
                            //TODO build duration and level should be set by deceasedBuildingDuration and SoldierBuildLevel
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        HashMap<Soldier, Integer> soldierAndTimeUnderConstruction = new HashMap<Soldier, Integer>();
                        soldierAndTimeUnderConstruction.put(newSoldier, 0);
                        underConstructionSoldiers.add(soldierAndTimeUnderConstruction);
                    }
                } else {
                    throw new NotEnoughResourcesException();
                }
            }
        }
    }
    private void addToConstructionTime(){
        if(underConstructionSoldiers.size()==0)
            return;;
        Soldier soldier=(Soldier)underConstructionSoldiers.get(0).keySet().toArray()[0];
        underConstructionSoldiers.get(0).replace(soldier,underConstructionSoldiers.get(0).get(soldier)+1);
    }

    public void transferToCamp( ArrayList<Camp> camps) { //should be called in each turn
            addToConstructionTime();
            if(underConstructionSoldiers.size()==0)
                return;
            Soldier soldier = (Soldier) underConstructionSoldiers.get(0).keySet().toArray()[0];
            if ( underConstructionSoldiers.get(0).get(soldier) == soldier.getBuildDuration()) {
                for (Camp camp : camps
                        ) {
                    if (camp.getSoldiers().size() < camp.getCapacity()) {
                        camp.getSoldiers().add(soldier);
                        break;
                    }
                }
                underConstructionSoldiers.remove(0);
            }

    }

    public HashMap<String, Integer> determineAvailableSoldiers(int gold, int elixir) {
        HashMap<String, Integer> availableSoldiers = new HashMap<String, Integer>(); //number of soldiers we cannot build is 0
        for (Soldier soldier : Soldier.getSoldierSubClasses()
                ) {
            if (soldier.getUnlockLevel() <= getLevel()) {
                int number = 0;
                number = gold / soldier.getCost();
                availableSoldiers.put(soldier.getClass().getSimpleName(), number);
            } else {
                availableSoldiers.put(soldier.getClass().getSimpleName(), 0);
            }
        }
        return availableSoldiers;
    }

    public int getTimeDecreasedToMakeASoldier() {
        return timeDecreasedToMakeASoldier;
    }

    public ArrayList<HashMap<Soldier, Integer>> getUnderConstructionSoldiers() {
        return underConstructionSoldiers;
    }
}
