package com.company.Controller;

import com.company.Exception.*;
import com.company.Models.*;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Cell;
import com.company.View.Regex;
import com.company.View.View;

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
        boolean commandMatched = false;
        while (!input.matches(Regex.SAVING_GAME_REGEX)) {
            switch (input) {
                case "showBuildings":
                    game.showBuildings();
                    this.getCommandInBuildings();
                    commandMatched = true;
                    break;
                case "resources":
                    game.showResources();
                    commandMatched = true;
                    break;
                case "attack":
                    implementAttackCommand();
                    commandMatched = true;
                    break;
            }
            if (input.matches(Regex.PASSING_TURN_REGEX)) {
                Matcher matcher = makePatternAndMatcher(input, Regex.PASSING_TURN_REGEX);
                if (matcher.find()) {
                    for (int turn = 0; turn < Integer.parseInt(matcher.group(1)); turn++) {
                        game.passTurn();
                    }
                }
                commandMatched = true;
            }

            if (!commandMatched) {
                View.show("invalid command please try again");
            }
            input = view.getInput();
        }
        Matcher matcher = makePatternAndMatcher(input, Regex.SAVING_GAME_REGEX);
        if (matcher.find()) {
            implementFinishGame(matcher.group(1), matcher.group(2));
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
                game = gameCenter.loadGame(startingCommand.trim().split("load")[1].trim());
            } catch (NotValidFilePathException e) {
                e.showExceptionMassage();
            }
        } else {
            View.show("please enter your preferred path for a saved game or start a new game");
            implementStartGame();
        }

    }

    private void implementFinishGame(String pathname, String name) {
        int flag = 1;
        while (flag == 1) {
            try {
                gameCenter.saveGame(game, pathname, name);
                flag = 0;
            } catch (NotValidFilePathException e) {
                e.showExceptionMassage();
            }
        }
    }

    public void implementBuildATowerCommand() throws NotEnoughFreeBuildersException, NotEnoughResourcesException { //name and place to be refactored ... was implemented to complete building a tower//available buildings in barracks command
        String availableBuildings = game.getVillage().getMainBuilding().findAvailableBuildings(game.getVillage().getResource().getGold(), game.getVillage().getResource().getElixir());
        View.show(availableBuildings);
        int numberOfAvailableBuildings = availableBuildings.split("\n").length;
        String playerChoice = view.getInput("Enter your preferred number in the list");
        if (!playerChoice.matches("\\d+") || Integer.parseInt(playerChoice) > numberOfAvailableBuildings) {
            View.show("invalid Choice");
            implementBuildATowerCommand();
            return;
        }
        Integer chosenNumber = Integer.parseInt(playerChoice);
        if (chosenNumber == numberOfAvailableBuildings) {
            game.getVillage().getMainBuilding().showMenu();
            getCommandInBuilding(game.getVillage().getMainBuilding());
            return;
        }
        Integer nextNumber = chosenNumber + 1;
        String buildingName = availableBuildings.substring(availableBuildings.indexOf(chosenNumber.toString() + ". "), availableBuildings.indexOf(nextNumber.toString()));
        buildingName = buildingName.split("\\.")[1].trim();
        Builder builder = null;
        builder = game.getVillage().findFreeBuilder();
        ArrayList<Cell> cellKinds = new ArrayList<Cell>(Cell.getCellKinds());
        for (Cell cell : Cell.getCellKinds()) {
            if (cell.getClass().getSimpleName().equals(buildingName)) {
                Class spacialBuilding = cell.getClass();
                try {
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_ELIXIR_COST");
                    View.show("Do you want to build " + buildingName + " for " + goldCost + " gold and " + elixirCost + " elixir? [Y/N]");
                    switch (view.getInput()) {
                        case "Y":
                            if (goldCost > game.getVillage().getResource().getGold() || elixirCost > game.getVillage().getResource().getElixir()) {
                                throw new NotEnoughResourcesException();
                            } else {
                                view.showMap(game.getVillage(), 0);
                                int flag = 0;
                                while (flag == 0)
                                    try {
                                        View.show("where do you want to build" + splitClassNameIntoWords(newCell.getClass().getSimpleName()));
                                        String[] coordinates = view.getInput().split("[(,)]");
                                        newCell.setY(Integer.parseInt(coordinates[2]) - 1);
                                        newCell.setX(Integer.parseInt(coordinates[1]) - 1);
                                        game.getVillage().buildTower(newCell);
                                        flag = 1;
                                    } catch (MarginalTowerException e) {
                                        e.showMessage();

                                    } catch (BusyCellException e) {
                                        e.showMessage();
                                    }

                                newCell.setWorkingBuilder(builder);
                                builder.setOccupationState(true);
                                Resource resource = new Resource(game.getVillage().getResource().getGold() - newCell.getGoldCost(), game.getVillage().getResource().getElixir() - newCell.getElixirCost());
                                game.getVillage().setResource(resource);
                                implementBuildATowerCommand();
                            }
                            break;
                        case "N": {
                            implementBuildATowerCommand();
                            return;
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void implementBuildSoldiers(Barrack barrack) throws unAvailableSoldierException, NotEnoughResourcesException, NotEnoughCapacityInCampsException {
        StringBuilder result = new StringBuilder();
        int index = 1;
        HashMap<String, Integer> availableSoldiers = barrack.determineAvailableSoldiers(game.getVillage().getResource().getElixir());
        for (String soldier : availableSoldiers.keySet()) {
            if (availableSoldiers.get(soldier) != 0) {
                result.append(index).append(". ").append(soldier).append(" Ax").append(availableSoldiers.get(soldier)).append("\n");
            } else {
                result.append(index).append(". ").append(soldier).append(" U").append("\n");
            }
            index++;
        }
        View.show(result.toString().trim());
        String playerChoice = view.getInput("Enter your preferred soldier name in the list");
        if (availableSoldiers.get(playerChoice) == 0) {
            throw new unAvailableSoldierException();
        } else {
            int soldierAmount = Integer.parseInt(view.getInput("How many of this soldier do you want to build?"));
            int totalCapacity = 0;
            for (Camp camp : game.getVillage().getCamps()) {
                if (!camp.getUnderConstructionStatus())
                    totalCapacity += camp.getCapacity() - camp.getSoldiers().size();
            }
            if (soldierAmount > totalCapacity) {
                throw new NotEnoughCapacityInCampsException();
            }
            barrack.buildSoldier(soldierAmount, playerChoice, availableSoldiers);
            Resource resource = new Resource(game.getVillage().getResource().getGold(), game.getVillage().getResource().getElixir() - soldierAmount * Config.getDictionary().get(playerChoice + "_ELEXIR_COST"));
            game.getVillage().setResource(resource);
        }
    }


    public void implementAttackCommand() {
        StringBuilder result = new StringBuilder("1. load map\n");
        int index = 2;
        for (Game game : game.getAllAttackedVillages()) {
            result.append(index).append(". ").append(game.getPlayerName()).append("\n");
            index++;
        }
        result.append(index + ". Back");
        View.show(result.toString());
        int choice = Integer.parseInt(view.getInput("please enter your preferred path for a saved game or select one from the list"));
        if (choice == 1) {
            boolean flag = false;
            String path = view.getInput("Enter map path");
            Game enemyGame = null;
            while (!flag) {
                try {
                    enemyGame = gameCenter.loadEnemyMap(path);
                    game.setAttackedVillage(enemyGame);
                    flag = true;
                } catch (NotValidFilePathException e) {
                    e.showExceptionMassage();
                    path = view.getInput("Enter map path");
                    if (path.equals("back") || path.equals("2")) {
                        implementAttackCommand();
                    }
                }
            }
            if (!game.getAllAttackedVillages().contains(enemyGame)) {
                game.getAllAttackedVillages().add(enemyGame);
            }

        } else if (choice == index) {
            mainCommandAnalyzer();
        } else {
            game.setAttackedVillage(game.getAllAttackedVillages().get(choice - 2));
        }

        implementAttackInEnemMap();
    }

    public void implementAttackInEnemMap() {

        View.show(game.showEnemyMapMenu());
        switch (Integer.parseInt(view.getInput("Enter your preferred number in the list"))) {

            case 1:
                View.show(game.showEnemyMapInfo(game.getAttackedVillage().getVillage()));
                implementAttackInEnemMap();
                break;
            case 2:
                startAttack();
                String userInput;
                do {
                    userInput = view.getInput();
                    switch (userInput) {
                        case "Go next turn":
                            game.passTurn();
                        case "put unit":
                            view.showMap(game.getAttackedVillage().getVillage(), 1);
                            String unit = view.getInput();
                            implementPutUnitCommand(unit);
                            break;
                        case "status resources":
                            View.show(game.statusResourcesInWar());
                        case "status units":
                            View.show(game.statusUnits());
                        case "status towers":
                            View.show(game.statusTowers());
                        case "status all":
                            View.show(game.statusAll());
                    }
                    Matcher unitMatcher = makePatternAndMatcher(userInput, Regex.STATUS_UNIT_TYPE);
                    Matcher towerMatcher = makePatternAndMatcher(userInput, Regex.STATUS_TOWER_TYPE);
                    if (unitMatcher.find()) {
                        View.show(game.statusUnit(unitMatcher.group(1)));
                    }
                    if (towerMatcher.find()) {
                        View.show(game.statusTower(towerMatcher.group(1)));
                    }
                }
                while (!userInput.equals("Quit attack") || !game.isWarFinished());
                View.show("The war ended with" + game.getGainedResource().getGold() + " gold," + game.getGainedResource().getElixir() + " elixir and" + game.getVillage().getScore() + "scores achieved!");
                game.healAfterWar();
                break;
            case 3:
                implementAttackCommand();
                break;
        }
    }

    private void implementPutUnitCommand(String unit) {
        Matcher matcher = makePatternAndMatcher(unit, Regex.PUT_UNIT_REGEX);
        if (matcher.find()) {
            try {
                game.putUnit(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)) - 1, Integer.parseInt(matcher.group(4)) - 1);
            } catch (MoreThanLimitSoldiersException e) {
                e.showMessage();
            } catch (InvalidPlaceForSoldiersException e) {
                e.showMessage();
            } catch (NotEnoughSoldierInTroopsException e) {
                e.showMessage();
            }
        }
    }

    public void startAttack() {
        outer:
        while (true) {
            // TODO: 4/30/18 maybe "Start Select" should have been implemented!!!
            String playerChoice = view.getInput("please select units you want to bring to war or type End select to go back to attack.");
            if (playerChoice.equals("End select")) {
                break;
            }
            if (!playerChoice.matches("Select \\w+ \\d+")) {
                View.show("invalid input.");
            } else {
                String[] splitPlayerChoice = playerChoice.split(" ");
                for (int i = 0; i < Integer.parseInt(splitPlayerChoice[2]); i++) {
                    try {
                        game.selectUnit(splitPlayerChoice[1]);
                    } catch (NoSuchSoldierInCampException e) {
                        e.showMessage(splitPlayerChoice[1]);
                        continue outer;
                    }
                }
            }
        }
        view.showMap(game.getVillage(), 1);
        String putUnitChoice = view.getInput("Enter the type of the soldier and its coordinates you want to use");
        while (!putUnitChoice.equals("Go next turn")) {
            implementPutUnitCommand(putUnitChoice);
            putUnitChoice = view.getInput("Enter the type of the soldier and its coordinates you want to use");
        }
        game.passTurn();

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
        if (input.equals("back")) {
            Game.setWhereIAm("You are in village");
            mainCommandAnalyzer();
            return;
        }
        if (matcher.find()) {
            int buildingNumber = Integer.parseInt(matcher.group(2));
            String buildingName = matcher.group(1).replace(" ", "");
            for (Cell[] cells : game.getVillage().getMap()) {
                for (Cell cell : cells) {
                    if (cell.getClass().getSimpleName().equalsIgnoreCase(buildingName)) {
                        cell.showMenu();
                        getCommandInBuilding(cell);
                        return;

                    }
                }
            }
        }
        View.show("no such building");
        getCommandInBuildings();
    }

    private void getCommandInBuilding(Cell cell) {
        switch (cell.getClass().getSimpleName()) {
            case ("Barrack"):
                getCommandInBarrack(cell);
                break;
            case ("Camp"):
                getCommandInCamp(cell);
                break;
            case ("ElixirMine"):
            case ("GoldMine"):
                getCommandInMine(cell);
                break;
            case ("ElixirStorage"):
            case ("GoldStorage"):
                getCommandStorage(cell);
                break;
            case ("MainBuilding"):
                getCommandInMainBuilding(cell);
                break;
            case ("AirDefence"):
            case ("ArcherTower"):
            case ("Cannon"):
            case ("WizardTower"):
                getCommandInDefence(cell);
                break;
            case ("Trap"):
            case ("Wall"):
                break;
        }
    }

    private void getCommandStorage(Cell cell) {
        Game.setWhereIAm("you are in Storage Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandStorage(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandStorage(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                getCommandInStorageInfoMenu(cell);
            case 2:
                game.showBuildings();
                getCommandInBuildings();
                break;
            default:
                View.show("invalid choice");
                break;


        }
    }

    private void getCommandInStorageInfoMenu(Cell cell) {
        Game.setWhereIAm("you are in Storage Info Menu");
        cell.showInfoMenu();
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInStorageInfoMenu(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInStorageInfoMenu(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                cell.showOverallInfo();
                getCommandInStorageInfoMenu(cell);
                break;
            case 2:
                cell.showUpgradeInfo();
                getCommandInStorageInfoMenu(cell);
                break;
            case 3:
                Storage storage = (Storage) cell;
                if (storage.getClass().getSimpleName().equals("GoldStorage")) {
                    View.show(storage.getSourcesInfo(new ArrayList<>(game.getVillage().getGoldStorages()), "gold storage"));
                } else {
                    View.show(storage.getSourcesInfo(new ArrayList<>(game.getVillage().getElixirStorages()), "elixir storage"));
                }
                getCommandInStorageInfoMenu(cell);
                break;
            case 4:
                try {
                    implementUpgradeCommand(cell);
                } catch (NotEnoughResourcesException e) {
                    e.showMessage();
                } catch (BarrackUpgradeException e) {
                    e.showMessage();
                }
                getCommandInStorageInfoMenu(cell);
                break;
            case 5:
                cell.showMenu();
                getCommandStorage(cell);
                break;
            default:
                View.show("invalid choice");
                getCommandInStorageInfoMenu(cell);
        }
    }

    private void implementUpgradeCommand(Cell cell) throws NotEnoughResourcesException, BarrackUpgradeException {
        View.show("Do you want to upgrade " + cell.getName() + " for " + cell.getUpgradeCost() + " golds? [Y/N]");
        switch (view.getInput()) {
            case "Y":
                if (cell.getUpgradeCost() > game.getVillage().getResource().getGold()) {
                    throw new NotEnoughResourcesException();
                } else {
                    if (cell.getClass().getSimpleName().equals("Barrack")) {
                        if (game.getVillage().getMainBuilding().getLevel() == cell.getLevel()) {
                            throw new BarrackUpgradeException();
                        }
                    }
                    cell.upgrade();
                    game.getVillage().getResource().setGold(game.getVillage().getResource().getGold() - cell.getUpgradeCost());
                }
                break;
            case "N":
                cell.showMenu();
                getCommandInBuilding(cell);
        }
    }

    private void getCommandInMine(Cell cell) {
        Game.setWhereIAm("you are in Mine Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInMine(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInMine(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                getCommandInInfoMenu(cell);
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
                getCommandInMine(cell);
                break;
            case 3:
                game.showBuildings();
                getCommandInBuildings();
                break;
        }
    }

    private void getCommandInCamp(Cell cell) {
        Game.setWhereIAm("you are in Camp Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInCamp(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInCamp(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                getCommandInCampInfoMenu((Camp) cell);
                break;
            case 2:
                Camp camp = (Camp) cell;
                View.show(camp.showSoldiers());
                getCommandInCamp(cell);
                break;
            case 3:
                game.showBuildings();
                getCommandInBuildings();
                break;
        }
    }

    private void getCommandInCampInfoMenu(Camp camp) {
        Game.setWhereIAm("you are in camp Info Menu");
        camp.showInfoMenu();
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                camp.showOverallInfo();
                getCommandInCampInfoMenu(camp);
                break;
            case 2:
                camp.showUpgradeInfo();
                getCommandInCampInfoMenu(camp);
                break;
            case 3:
                camp.showCapacityInfo(game.getVillage().getCamps());
                getCommandInCampInfoMenu(camp);
                break;
            case 4:
                getCommandInCamp(camp);
                break;
        }
    }

    private void getCommandInBarrack(Cell cell) {
        Game.setWhereIAm("you are in Barrack Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInBarrack(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInBarrack(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                getCommandInInfoMenu(cell);
                break;
            case 2:
                try {
                    implementBuildSoldiers((Barrack) cell);
                } catch (unAvailableSoldierException e) {
                    e.showMessage();
                } catch (NotEnoughResourcesException e) {
                    e.showMessage();
                } catch (NotEnoughCapacityInCampsException e) {
                    e.showMessage();
                }
                getCommandInBarrack(cell);
                break;
            case 3:
                View.show(game.getVillage().showBarracksStatus());
                getCommandInBarrack(cell);
                break;
            case 4:
                game.showBuildings();
                getCommandInBuildings();
                break;
            default:
                View.show("invalid input please try Again");
                getCommandInBarrack(cell);
        }
    }

    private void getCommandInDefence(Cell cell) {
        Game.setWhereIAm("you are in Defence Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInDefence(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInDefence(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1: //info
                getCommandInDefenceInfoMenu(cell);
                break;
            case 2: //Target
                StringBuilder damageAndRange = new StringBuilder();
                if (cell.getClass().getSimpleName().equals("ArcherTower")) {
                    View.show("Target: Ground units");
                }
                if (cell.getClass().getSimpleName().equals("AirDefence")) {
                    View.show("Target: Flying units");
                }
                if (cell.getClass().getSimpleName().equals("Cannon")) {
                    View.show("Target: Ground units");
                }
                if (cell.getClass().getSimpleName().equals("WizardTower")) {
                    View.show("Target: Ground & Flying units");
                }
                //damageAndRange.append("Damage: ").append(cell.getDamage()).append("\nDamage Range: ").append(cell.getRange());
                //View.show(damageAndRange.toString().trim());
                cell.showMenu();
                getCommandInDefence(cell);
                break;
            case 3:
                game.showBuildings();
                getCommandInBuildings();
                break;
            default:
                View.show("invalid choice");
                cell.showMenu();
                getCommandInDefence(cell);
        }
    }

    private void getCommandInDefenceInfoMenu(Cell cell) {
        Game.setWhereIAm("you are in Defence Info Menu");
        cell.showInfoMenu();
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInDefenceInfoMenu(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInDefenceInfoMenu(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                cell.showOverallInfo();
                getCommandInDefenceInfoMenu(cell);
                break;
            case 2:
                cell.showUpgradeInfo();
                getCommandInDefenceInfoMenu(cell);
                break;
            case 3:
                StringBuilder damageAndRange = new StringBuilder();
                if (cell.getClass().getSimpleName().equals("ArcherTower")) {
                    View.show("Target: Ground units");
                }
                if (cell.getClass().getSimpleName().equals("AirDefence")) {
                    View.show("Target: Flying units");
                }
                if (cell.getClass().getSimpleName().equals("Cannon")) {
                    View.show("Target: Ground units");
                }
                if (cell.getClass().getSimpleName().equals("WizardTower")) {
                    View.show("Target: Ground & Flying units");
                }
                damageAndRange.append("Damage: ").append(cell.getDamage()).append("\nDamage Range: ").append(cell.getRange());
                View.show(damageAndRange.toString());
                getCommandInDefenceInfoMenu(cell);
                break;
            case 4:
                cell.showMenu();
                getCommandInBuilding(cell);
                break;
        }
    }

    private void getCommandInMainBuilding(Cell cell) {
        Game.setWhereIAm("you are in Main Building Menu");
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInMainBuilding(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInMainBuilding(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                getCommandInInfoMenu(cell);
            case 2:
                int flag = 0;
                while (flag == 0) {
                    try {
                        implementBuildATowerCommand();
                        flag = 1;
                    } catch (NotEnoughFreeBuildersException e) {
                        e.showMessage();

                    } catch (NotEnoughResourcesException e) {
                        e.showMessage();
                    }
                }
                cell.showMenu();
                break;

            case 3:
                View.show(game.getVillage().showTownHallStatus().trim());
                cell.showMenu();
                getCommandInBuilding(cell);
                break;
            case 4:
                game.showBuildings();
                getCommandInBuildings();
                break;
        }
    }

    private void getCommandInInfoMenu(Cell cell) {
        Game.setWhereIAm("you are in " + cell.getClass().getSimpleName() +  "Info Menu");
        cell.showInfoMenu();
        String input = view.getInput("Enter your preferred number in the list");
        if (input.equals("resources")){
            game.showResources();
            getCommandInInfoMenu(cell);
            return;
        }
        if (input.equals("WhereIAm")){
            View.show(Game.getWhereIAm());
            getCommandInInfoMenu(cell);
            return;
        }
        int playerChoice = Integer.parseInt(input);
        switch (playerChoice) {
            case 1:
                cell.showOverallInfo();
                getCommandInInfoMenu(cell);
                break;
            case 2:
                cell.showUpgradeInfo();
                getCommandInInfoMenu(cell);
                break;
            case 3:
                cell.showMenu();
                getCommandInBuilding(cell);
                break;
        }
    }
}
