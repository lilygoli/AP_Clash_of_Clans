package com.company.Models;

import com.company.Exception.BusyCellException;
import com.company.Exception.MarginalTowerException;
import com.company.Exception.NotEnoughFreeBuildersException;
import com.company.Exception.NotEnoughResourcesException;
import com.company.View;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Controller {
    private Game game = null;
    private GameCenter gameCenter = new GameCenter();
    private View view = new View();

    public void implementBuildATowerCommand() throws NotEnoughFreeBuildersException, NotEnoughResourcesException, BusyCellException, MarginalTowerException { //name and place to be refactored ... was implemented to complete building a tower//available buildings in barracks command
        String availableBuildings = game.getVillage().getMainBuilding().findAvailableBuildings(game.getVillage().getResource().getGold(), game.getVillage().getResource().getElixir());
        View.show(availableBuildings);
        String playerChoice = view.getInput("Enter your preferred number in the list");
        if (playerChoice.equalsIgnoreCase("back")) {
            return;//ya back exc throw kone ke too main building menu return kone
        }
        Integer chosenNumber = Integer.parseInt(playerChoice);
        Integer nextNumber = chosenNumber + 1;
        String buildingName = availableBuildings.substring(availableBuildings.indexOf(chosenNumber.toString() + ". "), availableBuildings.indexOf(nextNumber.toString()));
        Builder builder = null;
        builder = game.getVillage().findFreeBuilder();//kollan bara 3 ta exc tooye tabeye bala bayad exc.showMessage seda konim
        ArrayList<Cell> cellKinds = new ArrayList<Cell>(Cell.getCellKinds());
        for (Cell cell : Cell.getCellKinds()) {
            if (cell.getClass().getSimpleName().equals(buildingName)) {
                Class spacialBuilding = cell.getClass();
                try {
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class,int.class).newInstance(0,0);
                    int goldCost = Config.getDictionary().get(newCell.getClass() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass() + "_ELIXIR_COST");
                    View.show("Do you wat to build" + buildingName + "for" + goldCost + "gold and" + elixirCost + "elixir? [Y/N]");
                    switch (view.getInput()) {
                        case "Y": {
                            if (goldCost > game.getVillage().getResource().getGold() || elixirCost > game.getVillage().getResource().getElixir()) {
                                throw new NotEnoughResourcesException();
                            } else {
                                view.showMap(game.getVillage(),0);
                                View.show("where do you want to build" + splitClassNameIntoWords(newCell.getClass().getSimpleName()));
                                String[] coordinates = view.getInput().split("[(,)]");
                                newCell.setY(Integer.parseInt(coordinates[1]));
                                newCell.setX(Integer.parseInt(coordinates[0]));
                                game.getVillage().buildTower(newCell);
                                newCell.setWorkingBuilder(builder);
                                builder.setOccupationState(true);
                            }
                        }
                        case "N": {
                            return;
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void implementAttackCommand() {
        game.startAttack();
        //while(command.equals("Quit attack")|| game.isWarFinished()){
        //String attackCommand=view.getInput;
        //switch(attackCommand){
        //case "Go next turn":
        //case "put unit":
    //}
        //View.show("The war ended with"+game.getGainedResources().getGold()+" gold,"+game.getGainedResources().getElixir()+" elixir and"+game.getScore()+"scores achieved!");
        //game.healAfterWar
    }

    private String splitClassNameIntoWords(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.split("(?<!^)(?=[A-Z])").length; i++) {
            result.append(name.split("(?<!^)(?=[A-Z])")[i]).append(" ");
        }
        return result.toString().trim();
    }
}
