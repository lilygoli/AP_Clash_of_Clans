package com.company.Models;

import com.company.Exception.*;
import com.company.Models.Buildings.*;
import com.company.View;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Game game = null;
    private GameCenter gameCenter = new GameCenter();
    private View view = new View();

    public void mainCommandAnalyzer() {
        while (game == null) {
            implementStartGame();
        }
        String input = view.getInput();
        while (!input.matches(Regex.SAVING_GAME_REGEX)) {
            if (Game.getWhereIAm().equals("You are in village")) {
                switch (input) {
                    case "showBuildings":
                        game.showBuildings();
                        this.getCommandInBuildings();
                        break;
                    case "resources":
                        game.statusResources();
                        break;
                }
            }


            if (input.matches(Regex.PASSING_TURN_REGEX)) {
                Matcher matcher = makePatternAndMatcher(input, Regex.PASSING_TURN_REGEX);
                if (matcher.find()) {
                    for (int turn = 0; turn < Integer.parseInt(matcher.group(0)); turn++) {
                        game.passTurn();
                    }
                }
            }


            input = view.getInput();
        }
        Matcher matcher = makePatternAndMatcher(input, Regex.SAVING_GAME_REGEX);
        if (matcher.find()) {
            implementFinishGame(matcher.group(0), matcher.group(1));
        }
    }

    private Matcher makePatternAndMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
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

    private void implementFinishGame(String pathname, String name) {
        gameCenter.saveGame(game, pathname, name);
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
        buildingName = buildingName.split("\\.")[1].trim();
        Builder builder = null;
        builder = game.getVillage().findFreeBuilder();//kollan bara 3 ta exc tooye tabeye bala bayad exc.showMessage seda konim
        ArrayList<Cell> cellKinds = new ArrayList<Cell>(Cell.getCellKinds());
        for (Cell cell : Cell.getCellKinds()) {
            if (cell.getClass().getSimpleName().equals(buildingName)) {
                Class spacialBuilding = cell.getClass();
                try {
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_ELIXIR_COST");
                    View.show("Do you wat to build " + buildingName + " for " + goldCost + " gold and " + elixirCost + " elixir? [Y/N]");
                    switch (view.getInput()) {
                        case "Y": {
                            if (goldCost > game.getVillage().getResource().getGold() || elixirCost > game.getVillage().getResource().getElixir()) {
                                throw new NotEnoughResourcesException();
                            } else {
                                view.showMap(game.getVillage(), 0);
                                View.show("where do you want to build" + splitClassNameIntoWords(newCell.getClass().getSimpleName()));
                                String[] coordinates = view.getInput().split("[(,)]");
                                newCell.setY(Integer.parseInt(coordinates[1]));
                                newCell.setX(Integer.parseInt(coordinates[2]));
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

    public void implementBuildSoldiers(Barrack barrack) throws unAvailableSoldierException, NotEnoughResourcesException {
        StringBuilder result = new StringBuilder();
        int index = 0;
        HashMap<String, Integer> availableSoldiers = barrack.determineAvailableSoldiers(game.getVillage().getResource().getGold(), game.getVillage().getResource().getElixir());
        for (String soldier : availableSoldiers.keySet()) {
            if (availableSoldiers.get(soldier) != 0) {
                result.append(index).append(". ").append(soldier).append(" Ax").append(availableSoldiers.get(soldier));
            } else {
                result.append(index).append(". ").append(soldier).append("U");
            }
            View.show(result.toString());
            String playerChoice = view.getInput("Enter your preferred soldier name in the list");
            if (availableSoldiers.get(playerChoice) == 0) {
                throw new unAvailableSoldierException();
            } else {
                int soldierAmount = Integer.parseInt(view.getInput("How many of this soldier do you want to build?"));
                barrack.buildSoldier(soldierAmount, soldier, availableSoldiers);
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

    private void getCommandInBuildings() {
        String input = view.getInput();
        Matcher matcher = makePatternAndMatcher(input, Regex.SELECT_BUILDING_REGEX);
        if (matcher.find()) {
            int buildingNumber = Integer.parseInt(matcher.group(2));
        }
        String buildingName = matcher.group(1).replace(" ", "");
        for (Cell[] cells : game.getVillage().getMap()) {
            for (Cell cell : cells) {
                if (cell.getClass().getSimpleName().equalsIgnoreCase(buildingName)) {
                    cell.showMenu();
                    getCommandInBuilding(cell);
                }
            }
        }
    }

    private void getCommandInBuilding(Cell cell) {
        int playerChoice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
        switch (cell.getClass().getSimpleName()) {
            case ("Barrack"):
                getCommandInBarrack(playerChoice, cell);
                break;
            case ("Camp"):
                getCommandInCamp(playerChoice, cell);
                break;
            case ("ElixirMine"):
            case ("GoldMine"):
                getCommandInMine(playerChoice, cell);
                break;
            case ("ElixirStorage"):
            case ("GoldStorage"):
                getCommandStorage(playerChoice, cell);
                break;
            case ("MainBuilding"):
                getCommandInMainBuilding(playerChoice, cell);
                break;
            case ("AirDefence"):
            case ("ArcherTower"):
            case ("Cannon"):
            case ("WizardTower"):
                getCommandInDefence(playerChoice, cell);
                break;
            case ("Trap"):
            case ("Wall"):
                break;
        }
    }

    private void getCommandStorage(int playerChoice, Cell cell) {
        if (playerChoice == 1) {
            cell.showInfoMenu();
            int choice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
            switch (choice) {
                case 1:
                    cell.showOverallInfo();
                    break;
                case 2:
                    cell.showUpgradeInfo();
                    break;
                case 3:
                    Storage storage = (Storage) cell;
                    if (storage.getClass().getSimpleName().equals("GoldStorage")) {
                        View.show(storage.getSourcesInfo(new ArrayList<>(game.getVillage().getGoldStorages()), "gold storage"));
                    } else {
                        View.show(storage.getSourcesInfo(new ArrayList<>(game.getVillage().getElixirStorages()), "elixir storage"));
                    }
                    break;
                case 4:
                    try {
                        implementUpgradeCommand(cell);
                    } catch (NotEnoughResourcesException e) {
                        e.showMessage();
                    }
                    //TODO what does upgrade do here?
                case 5:
                    return;//back
            }
        }
    }

    private void implementUpgradeCommand(Cell cell) throws NotEnoughResourcesException {
        View.show("Do you want to upgrade " + cell.getName() +  " for " + cell.getUpgradeCost() + " golds? [Y/N]");
        switch (view.getInput()){
            case "Y":
                if (cell.getUpgradeCost() > game.getVillage().getResource().getGold()){
                    throw new NotEnoughResourcesException();
                }
                else{
                    cell.upgrade();
                    game.getVillage().getResource().setGold(game.getVillage().getResource().getGold() - cell.getUpgradeCost());
                }
            case "N":
                // TODO: 4/29/2018 back or no
        }
    }

    private void getCommandInMine(int playerChoice, Cell cell) {
        switch (playerChoice) {
            case 1:
                cell.showInfoMenu();
                int choice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
                getCommandInInfoMenu(choice, cell);
                break;
            case 2:
                Mine mine = (Mine) cell;
                if (mine.getClass().getSimpleName().equals("GoldMine")) {
                    ArrayList<Storage> allGoldStorage = new ArrayList<>(game.getVillage().getGoldStorages());
                    mine.mine(allGoldStorage);
                } else {
                    ArrayList<Storage> allElixirStorage = new ArrayList<>(game.getVillage().getElixirStorages());
                    mine.mine(allElixirStorage);
                }
                break;

            case 3:
                return;//back
        }
    }

    private void getCommandInCamp(int playerChoice, Cell cell) {
        switch (playerChoice) {
            case 1:
                cell.showInfoMenu();
                int choice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
                getCommandInCampInfoMenu(choice, (Camp) cell);
                break;
            case 2:
                Camp camp = (Camp) cell;
                View.show(camp.showSoldiers());
                break;
            case 3:
                return;//back
        }
    }

    private void getCommandInCampInfoMenu(int playerChoice, Camp camp) {
        switch (playerChoice) {
            case 1:
                camp.showOverallInfo();
                break;
            case 2:
                camp.showUpgradeInfo();
                break;
            case 3:
                camp.showCapacityInfo(game.getVillage().getCamps());
                break;
            case 4:
                return;//back
        }
    }

    private void getCommandInBarrack(int playerChoice, Cell cell) {
        switch (playerChoice) {
            case 1:
                cell.showInfoMenu();
                int choice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
                getCommandInInfoMenu(choice, cell);
                break;
            case 2:
                try {
                    implementBuildSoldiers((Barrack) cell);
                } catch (unAvailableSoldierException e) {
                    e.showMessage();
                } catch (NotEnoughResourcesException e) {
                    e.showMessage();
                }
                break;
            case 3:
                View.show(game.getVillage().showBarracksStatus(game.getTime()));
                break;
            case 4:
                return;//back

        }
    }

    private void getCommandInDefence(int playerChoice, Cell cell) {
        switch (playerChoice) {
            case 1: //info
                cell.showInfoMenu();
                getCommandInDefenceInfoMenu(cell);
                break;
            case 2: //Target
                StringBuilder damageAndRange = new StringBuilder();
                if (cell.getClass().getSimpleName().equals("ArcherTower")) {
                    View.show("Target: Ground units\n");
                }
                if (cell.getClass().getSimpleName().equals("AirDefence")) {
                    View.show("Target: Flying units\n");
                }
                if (cell.getClass().getSimpleName().equals("Cannon")) {
                    View.show("Target: Ground units\n");
                }
                if (cell.getClass().getSimpleName().equals("WizardTower")) {
                    View.show("Target: Ground & Flying units\n");
                }
                damageAndRange.append("Damage: ").append(cell.getDamage()).append("\nDamage Range: ").append(cell.getRange());
                View.show(damageAndRange.toString());
                break;
            case 3: //back
                break;
        }
    }

    private void getCommandInDefenceInfoMenu(Cell cell) {
        int choice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
        switch (choice) {
            case 1:
                cell.showOverallInfo();
                break;
            case 2:
                cell.showUpgradeInfo();
                break;
            case 3:
                StringBuilder damageAndRange = new StringBuilder();
                if (cell.getClass().getSimpleName().equals("ArcherTower")) {
                    View.show("Target: Ground units\n");
                }
                if (cell.getClass().getSimpleName().equals("AirDefence")) {
                    View.show("Target: Flying units\n");
                }
                if (cell.getClass().getSimpleName().equals("Cannon")) {
                    View.show("Target: Ground units\n");
                }
                if (cell.getClass().getSimpleName().equals("WizardTower")) {
                    View.show("Target: Ground & Flying units\n");
                }
                damageAndRange.append("Damage: ").append(cell.getDamage()).append("\nDamage Range: ").append(cell.getRange());
                View.show(damageAndRange.toString());
                break;
            case 4: // TODO: 4/29/18 back
                break;
        }
    }

    private void getCommandInMainBuilding(int command, Cell cell) {
        switch (command) {
            case 1:
                cell.showInfoMenu();
                int playerChoice = Integer.parseInt(view.getInput("Enter your preferred number in the list"));
                getCommandInInfoMenu(playerChoice, cell);
                break;
            case 2:
                try {
                    implementBuildATowerCommand();
                } catch (NotEnoughFreeBuildersException e) {
                    e.showMessage();
                } catch (NotEnoughResourcesException e) {
                    e.printStackTrace();
                } catch (BusyCellException e) {
                    e.showMessage();
                } catch (MarginalTowerException e) {
                    e.showMessage();
                }
                break;
            case 3:
                View.show(game.getVillage().showTownHallStatus());
                break;
            case 4:
                return;//back
        }
    }

    private void getCommandInInfoMenu(int playerChoice, Cell cell) {
        switch (playerChoice) {
            case 1:
                cell.showOverallInfo();
                break;
            case 2:
                cell.showUpgradeInfo();
                break;
            case 3:
                return;//back
        }
    }
}
