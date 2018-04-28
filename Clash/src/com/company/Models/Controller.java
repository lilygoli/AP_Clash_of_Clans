package com.company.Models;

import com.company.Exception.*;
import com.company.View;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Game game = null;
    private GameCenter gameCenter = new GameCenter();
    private View view = new View();

    public void mainCommandAnalyzer() {
        implementStartGame();
        String input=view.getInput();
        while(input.matches(Regex.SAVING_GAME_REGEX)){
            if(input.matches(Regex.PASSING_TURN_REGEX)){
                Matcher matcher=makePatternAndMatcher(input,Regex.PASSING_TURN_REGEX);
                if(matcher.find()){
                    for (int turn = 0; turn < Integer.parseInt(matcher.group(0)); turn++) {
                        game.passTurn();
                    }
                }
            }




            input=view.getInput();
        }
        Matcher matcher=makePatternAndMatcher(input,Regex.SAVING_GAME_REGEX);
        if(matcher.find()){
            implementFinishGame(matcher.group(0),matcher.group(1));
        }
    }
    private Matcher makePatternAndMatcher(String input,String regex){
        Pattern pattern=Pattern.compile(regex);
        return pattern.matcher(input);
    }
    private void implementStartGame() {
        String startingCommand = view.getInput();
        if (startingCommand.matches("newGame")) {
            game = gameCenter.makeNewGame();
        } else if (startingCommand.matches("load (.+)")) {
            try {
                game = gameCenter.loadGame(startingCommand.trim().split("load")[0].trim());
            } catch (NotValidFilePathException e) {
                e.showExceptionMassage();
            }
        } else {
            View.show("please enter your preferred path for a saved game or start a new game");
            implementStartGame();
        }

    }
    private void implementFinishGame(String pathname,String name){
        gameCenter.saveGame(game,pathname,name);
    }

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
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass() + "_ELIXIR_COST");
                    View.show("Do you wat to build" + buildingName + "for" + goldCost + "gold and" + elixirCost + "elixir? [Y/N]");
                    switch (view.getInput()) {
                        case "Y": {
                            if (goldCost > game.getVillage().getResource().getGold() || elixirCost > game.getVillage().getResource().getElixir()) {
                                throw new NotEnoughResourcesException();
                            } else {
                                view.showMap(game.getVillage(), 0);
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
