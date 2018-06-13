package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Exception.*;
import com.company.Models.Builder;
import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Resource;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Buildings.Storage;
import com.company.Models.Towers.Cell;

import com.company.View.View;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SideBarUI {
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static Controller controller;
    private static Stage primaryStage;
    public static void setController(Controller controller) {
        SideBarUI.controller = controller;
    }

    public static void makeSideBar(Group group, boolean isInEnemyMap) {
        ImageView sideBarBackgroundImageView =getImageView("labelLessCroppedMenu.png");
        Double sideBarStartingX = -sideBarBackgroundImageView.getImage().getWidth() / 16;
        sideBarBackgroundImageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        sideBarBackgroundImageView.setScaleY(0.95);
        sideBarBackgroundImageView.setX(sideBarStartingX);
        sideBarBackgroundImageView.setY(-20);
        ImageView borderImageView;
        if(!isInEnemyMap){
            borderImageView = getImageView("upperBorder.png");
        }else {
             borderImageView = getImageView("AttackUpperBorder.png");
        }

            borderImageView.setScaleX(0.6);
            borderImageView.setScaleY(0.8);
            borderImageView.setY(40);
            borderImageView.setX(sideBarStartingX + 20);

        ImageView saveView = getImageView("save.png");
        saveView.setOnMouseClicked(event -> {
                makeSideBar(group,false);
                TextField pathTextField=new TextField("enter path");
                TextField nameTextField= new TextField("enter name");
                Button saveButton=new Button("save");
                pathTextField.setBackground(Background.EMPTY);
                pathTextField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
                pathTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pathTextField.setText("");
                    }
                });
                nameTextField.setBackground(Background.EMPTY);
                nameTextField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
                nameTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        nameTextField.setText("");
                    }
                });
                saveButton.setStyle("-fx-background-color: rgba(143,99,29,0.87)");
                VBox vBox = new VBox(10 , pathTextField , nameTextField , saveButton);
                vBox.relocate(UIConstants.BUTTON_STARTING_X + 10 , Screen.getPrimary().getVisualBounds().getHeight() * 0.2);
                group.getChildren().add(vBox);
                saveButton.setOnMouseClicked(event1 -> {
                    try {
                        MapUI.getShowMapAnimationTimer().stop();
                        controller.getGameCenter().saveGame(controller.getGame(), pathTextField.getText(), nameTextField.getText());
                        primaryStage.close();
                    } catch (NotValidFilePathException e) {
                        NotValidFilePathException exception = new NotValidFilePathException();
                        new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                        group.getChildren().add(exception.getImageView());
                    }
                });

        });
        saveView.setX(sideBarStartingX + 30);
        saveView.setY(Screen.getPrimary().getVisualBounds().getHeight() * 6 / 10);
        saveView.setScaleX(0.5);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        if(!isInEnemyMap) {
            makeResourceLabels(group,sideBarStartingX);
            group.getChildren().add(saveView);
        }else {
            makeResourceLabelsInAttack(group,sideBarStartingX);
        }

    }



    public static void makeStartingMenu(Group group, Stage stage){
        primaryStage=stage;
        makeSideBar(group,false);
        ImageView attackImage = getImageView("Attack.png");
        attackImage.setOnMouseClicked(event -> {
            makeLoadEnemyMapMenu(group);

        });
        attackImage.setScaleX(0.6);
        attackImage.setScaleY(0.8);
        attackImage.setY(UIConstants.ATTACK_STARTING_Y);
        attackImage.setX(UIConstants.ATTACK_STARTING_X);
        group.getChildren().add(attackImage);

    }

    private static void makeLoadEnemyMapMenu(Group group) {
        makeSideBar(group,false);
        StringBuilder eneMyMapsList = new StringBuilder("1. load map\n");
        int index = 2;
        for (Game game : controller.getGame().getAllAttackedVillages()) {
            eneMyMapsList.append(index).append(". ").append(game.getPlayerName()).append("\n");
            index++;
        }
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setBackground(Background.EMPTY);
        comboBox.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        comboBox.setMaxWidth(300);
        comboBox.setMaxWidth(300);
        comboBox.getItems().addAll(eneMyMapsList.toString().split("\n"));
        comboBox.relocate(90, UIConstants.MENU_VBOX_STARTING_Y);
        Button selectButton=new Button("select");
        selectButton.setStyle("-fx-background-color: #a5862e");
        selectButton.relocate(120,UIConstants.MENU_VBOX_STARTING_Y + 50);
        selectButton.setOnMouseClicked(event1 -> {
            loadEnemyMap(group, comboBox);
        });
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event3 -> {
            makeStartingMenu(group,primaryStage);
        });
        group.getChildren().add(selectButton);
        group.getChildren().add(backView);
        group.getChildren().add(comboBox);
    }

    private static void loadEnemyMap(Group group, ComboBox<String> comboBox) {
        if (comboBox.getValue().equals("1. load map")) {
            TextField textField= new TextField("please enter path");
            textField.relocate(UIConstants.BUTTON_STARTING_X,400);
            group.getChildren().add(textField);
            Button loadButton=new Button("load");
            group.getChildren().add(loadButton);
            loadButton.relocate(200,400);
            loadButton.setStyle("-fx-background-color: #a5862e");
            loadButton.setOnMouseClicked(event2 ->{
                Game enemyGame = null;
                    try {
                        enemyGame = controller.getGameCenter().loadEnemyMap(textField.getText());
                        controller.getGame().setAttackedVillage(enemyGame);
                        AttackMapUI.makeAttackGameBoard(primaryStage,controller);
                    } catch (NotValidFilePathException e) {
                        new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                        group.getChildren().add(e.getImageView());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                if (!controller.getGame().getAllAttackedVillages().contains(enemyGame)) {
                    controller.getGame().getAllAttackedVillages().add(enemyGame);
                }
            });
        } else {
            controller.getGame().setAttackedVillage(controller.getGame().getAllAttackedVillages().get(Integer.parseInt(comboBox.getValue().split("\\.")[0])-2));
            try {
                AttackMapUI.makeAttackGameBoard(primaryStage,controller);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void makeResourceLabels(Group group,Double sideBarStartingX) {
        Label gold= new Label(Integer.toString(controller.getGame().getVillage().getResource().getGold()));
        gold.relocate(sideBarStartingX+140,65);
        Label elixir = new Label(Integer.toString(controller.getGame().getVillage().getResource().getElixir()));
        elixir.relocate(sideBarStartingX+220,65);
        Label score = new Label(Integer.toString(controller.getGame().getVillage().getScore()));
        score.relocate(sideBarStartingX+180,100);
        group.getChildren().add(gold);
        group.getChildren().add(elixir);
        group.getChildren().add(score);
    }
    private static void makeResourceLabelsInAttack(Group group, Double sideBarStartingX) {
        Label gold= new Label(Integer.toString(controller.getGame().getAttackedVillage().getVillage().getResource().getGold()));
        gold.relocate(sideBarStartingX+130,65);
        Label elixir = new Label(Integer.toString(controller.getGame().getAttackedVillage().getVillage().getResource().getElixir()));
        elixir.relocate(sideBarStartingX+220,65);
        Label goldAchieved = new Label(Integer.toString(controller.getGame().getVillage().getGainedResource().getGold()));
        goldAchieved.relocate(sideBarStartingX+180,85);
        Label elixirAchieved = new Label(Integer.toString(controller.getGame().getVillage().getGainedResource().getElixir()));
        elixirAchieved.relocate(sideBarStartingX+180,105);
        group.getChildren().add(gold);
        group.getChildren().add(elixir);
        group.getChildren().add(goldAchieved);
        group.getChildren().add(elixirAchieved);

    }

    public static void makeMainBuildingMenu(Group group) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> {
            makeDefaultInfoMenu(group,controller.getGame().getVillage().getMap()[14][14]);
        });
        ImageView availableBuildingView = getImageView("AvailableBuildings.png");
        availableBuildingView.setOnMouseClicked(mouseEvent -> {
            showAvailableBuildings(group);
        });
        ImageView statusView = getImageView("Status.png");
        statusView.setOnMouseClicked(event -> {
            makeSideBar(group,false);
            makeLabels(group,controller.getGame().getVillage().showTownHallStatus().trim(),0.2,false);
            ImageView backView = getImageView("back.png");
            backView.setScaleX(0.5);
            backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
            backView.setX(UIConstants.BUTTON_STARTING_X);
            backView.setOnMouseClicked(backEvent -> {
                makeMainBuildingMenu(group);
            });
            group.getChildren().add(backView);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeStartingMenu(group,primaryStage);
        });
        VBox vBox = new VBox(infoView, availableBuildingView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static ImageView getImageView(String imageName) {
        File infoFile = new File(ADDRESS + imageName);
        Image infoImage = new Image(infoFile.toURI().toString());
        return new ImageView(infoImage);
    }

    private static void showAvailableBuildings(Group group) {
        makeSideBar(group,false);
        makeLabels(group,"choose your preferred building",0.17,false);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setBackground(Background.EMPTY);
        comboBox.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        String availableBuildings = controller.getGame().getVillage().getMainBuilding().findAvailableBuildings(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir());
        comboBox.getItems().addAll(availableBuildings.split("\n"));
        comboBox.relocate(70, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(comboBox);
        Button selectButton=new Button("select");
        selectButton.setStyle("-fx-background-color: #a5862e");
        selectButton.relocate(200,UIConstants.MENU_VBOX_STARTING_Y + 3);
        selectButton.setOnMouseClicked(event -> {
            showAvailableBuildings(group);
            implementBuildATowerCommand(comboBox.getValue(),group);
        });
        group.getChildren().add(selectButton);
        ImageView backView = getImageView("Back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event -> {
            makeMainBuildingMenu(group);
        });
        group.getChildren().add(backView);
    }

    public static void makeStorageMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        overAllInfoView.setOnMouseClicked(event -> {
            showOverallInfo(group , cell);
        });
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> {
            showUpgradeInfo(group , cell);
        });
        ImageView SourcesInfoView = getImageView("SourcesInfo.png");
        SourcesInfoView.setOnMouseClicked(event -> {
            showSourcesInfo(group , cell);
        });
        ImageView upgradeView = getImageView("Upgrade.png");
        upgradeView.setOnMouseClicked(event -> {
            implementUpgradeBuildings(group , cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeStartingMenu(group,primaryStage);
        });
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, SourcesInfoView, upgradeView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void showSourcesInfo(Group group, Cell cell) {
        Storage storage = (Storage)cell;
        makeSideBar(group,false);
        ImageView sourcesInfoInsides;
        if (storage.getClass().getSimpleName().equals("GoldStorage")) {
            sourcesInfoInsides = getImageView("sourcesInfoInsides2.png");
        }
        else{
            sourcesInfoInsides = getImageView("sourcesInfoInsides.png");
        }
        sourcesInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        sourcesInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(sourcesInfoInsides);
        Label label=new Label(Integer.toString(storage.getResource()));
        label.relocate(UIConstants.INFO_LABEL_STARTING_X  - 45, Screen.getPrimary().getVisualBounds().getHeight() * 0.355);
        label.setTextFill(Color.BROWN);
        label.setFont(Font.font("Papyrus",18));
        group.getChildren().add(label);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            backSwitchCaseFunction(group, cell);
        });
    }

    public static void makeCampMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> {
            makeCampInfoMenu(group , cell);
        });
        ImageView SoldiersView = getImageView("Soldiers.png");
        SoldiersView.setOnMouseClicked(event -> {
            makeSoldiersMenu(group , cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeStartingMenu(group,primaryStage);
        });
        VBox vBox = new VBox(1, infoView, SoldiersView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void makeSoldiersMenu(Group group, Cell cell) {
        Camp camp = (Camp)cell;
        makeSideBar(group,false);
        makeLabels(group,camp.showSoldiers().trim(),0.2,false);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            makeCampMenu(group  ,cell);
        });
    }

    public static void makeCampInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        overAllInfoView.setOnMouseClicked(event -> {
            showOverallInfo(group , cell);
        });
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> {
            showUpgradeInfo(group , cell);
        });
        ImageView CapacityInfoView = getImageView("CapacityInfo.png");
        CapacityInfoView.setOnMouseClicked(event -> {
            showCampCapacityInfo(group , cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeCampMenu(group , cell);
        });
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, CapacityInfoView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void showCampCapacityInfo(Group group, Cell cell) {
        Camp camp = (Camp)cell;
        makeSideBar(group,false);
        ImageView campCapacity= getImageView("campCapacity.png");
        campCapacity.setX(UIConstants.INFOMENU_STARTING_X);
        campCapacity.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(campCapacity);
        makeLabels(group,Integer.toString(camp.getCapacity()),0.39,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            makeCampInfoMenu(group  ,cell);
        });
    }

    public static void makeMineMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> {
            makeDefaultInfoMenu(group,cell);
        });
        ImageView mineView = getImageView("Mine.png");
        mineView.setOnMouseClicked(event -> {
            Mine mine = (Mine) cell;
            if (mine.getClass().getSimpleName().equals("GoldMine")) {
                ArrayList<Storage> allGoldStorage = new ArrayList<>(controller.getGame().getVillage().getGoldStorages());
                mine.mine(allGoldStorage);
            } else {
                ArrayList<Storage> allElixirStorage = new ArrayList<>(controller.getGame().getVillage().getElixirStorages());
                mine.mine(allElixirStorage);
            }
            makeMineMenu(group,cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
          makeStartingMenu(group,primaryStage);
        });
        VBox vBox = new VBox(1, infoView, mineView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void makeDefaultInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overallInfoView = getImageView("OverAllInfo.png");
        overallInfoView.setOnMouseClicked(event -> {
            showOverallInfo(group, cell);
        });
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> {
            showUpgradeInfo(group, cell);
        });
        ImageView upgradeView = getImageView("Upgrade.png");
        upgradeView.setOnMouseClicked(event -> {
            implementUpgradeBuildings(group, cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeBuildingsMenu(group,cell);
        });
        VBox vBox = new VBox(1, overallInfoView, UpgradeInfoView,upgradeView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void implementUpgradeBuildings(Group group, Cell cell) {
        if (cell.getClass().getSimpleName().equals("Barrack")) {
            if (cell.getLevel() + 1 > controller.getGame().getVillage().getMainBuilding().getLevel()) {
                ImageView upgradeError = new ImageView("BarrackUpgradeError.png");
                upgradeError.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.5);
                upgradeError.setX(UIConstants.BUTTON_STARTING_X);
                new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(upgradeError.imageProperty(), null))).play();
                group.getChildren().add(upgradeError);
                return;
            }
        }
        makeSideBar(group,false);
        ImageView upgradeQuestion = getImageView("UpgradeQuestion.png");
        upgradeQuestion.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.3);
        upgradeQuestion.setX(UIConstants.BUTTON_STARTING_X);
        Label cost = new Label(Integer.toString(cell.getUpgradeCost()));
        cost.setTextFill(Color.BROWN);
        cost.setScaleX(1.2);
        cost.relocate(UIConstants.BUTTON_STARTING_X + 60, Screen.getPrimary().getVisualBounds().getHeight() * 0.375);
        ImageView upgrade = getImageView("Upgrade.png");
        upgrade.setScaleX(0.6);
        upgrade.setX(UIConstants.BUTTON_STARTING_X);
        upgrade.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.45);
        group.getChildren().add(upgradeQuestion);
        group.getChildren().add(cost);
        group.getChildren().add(upgrade);
        upgrade.setOnMouseClicked(event1 -> {
            if (cell.getUpgradeCost() > controller.getGame().getVillage().getResource().getGold()) {
                NotEnoughResourcesException exception = new NotEnoughResourcesException();
                new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                group.getChildren().add(exception.getImageView());
            } else {
                cell.upgrade();
                Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold() - cell.getUpgradeCost(), controller.getGame().getVillage().getResource().getElixir());
                controller.getGame().getVillage().setResource(resource);
                backSwitchCaseFunction(group, cell);
            }
        });
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event -> {
            backSwitchCaseFunction(group, cell);
        });
        group.getChildren().add(backView);
    }


    private static void showUpgradeInfo(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView UpgradeCost= getImageView("UpgradeCost.png");
        UpgradeCost.setScaleX(0.6);
        UpgradeCost.setX(UIConstants.INFOMENU_STARTING_X);
        UpgradeCost.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(UpgradeCost);
        makeLabels(group,Integer.toString(cell.getUpgradeCost()),0.32,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            backSwitchCaseFunction(group, cell);
        });
    }

    private static void backSwitchCaseFunction(Group group, Cell cell) {
        switch (cell.getClass().getSimpleName()){
            case "GoldStorage" :
            case "ElixirStorage" :
                makeStorageMenu(group , cell);
                break;
            case "Camp":
                makeCampInfoMenu(group ,cell);
                break;
            case "Cannon":
            case "ArcherTower":
            case "WizardTower":
            case "AirDefence":
            case "Trap":
            case "Wall":
                makeDefencesInfoMenu(group , cell);
                break;
            default:
                makeDefaultInfoMenu(group,cell);
                break;
        }
    }

    private static void showOverallInfo(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overallInfoInsides= getImageView("OverallInfoInsides.png");
        overallInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        overallInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(overallInfoInsides);
        makeLabels(group,Integer.toString(cell.getLevel()),0.36,true);
        makeLabels(group,Integer.toString(cell.getStrength()),0.27,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            backSwitchCaseFunction(group, cell);
        });
    }

    public static void makeBarrackMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> {
            makeDefaultInfoMenu(group,cell);
        });
        ImageView BuildSoldiersView = getImageView("BuildSoldiers.png");
        ImageView statusView = getImageView("Status.png");
        statusView.setOnMouseClicked(event -> {
            makeStatusBarracks(group, cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            makeStartingMenu(group,primaryStage);
        });
        VBox vBox = new VBox(1, infoView, BuildSoldiersView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);

        BuildSoldiersView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                barrackBuildSoldierMenu(group, cell);
            }
        });
    }

    private static void makeStatusBarracks(Group group, Cell cell) {
        makeSideBar(group,false);
        makeLabels(group,controller.getGame().getVillage().showBarracksStatus(),0.2,false);
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(backEvent -> {
            makeBarrackMenu(group,cell);
        });
        group.getChildren().add(backView);
    }

    public static void makeBuildingsMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        switch (cell.getClass().getSimpleName()) {
            case "MainBuilding":
                makeMainBuildingMenu(group);
                break;
            case "AirDefence":
            case "ArcherTower":
            case "Cannon":
            case "Trap":
            case "WizardTower":
                makeDefencesMenu(group, cell);
                break;
            case "Barrack":
                makeBarrackMenu(group, cell);
                break;
            case "GoldMine":
            case "ElixirMine":
                makeMineMenu(group, cell);
                break;
            case "Camp":
                makeCampMenu(group, cell);
                break;
            case "GoldStorage":
            case "ElixirStorage":
                makeStorageMenu(group, cell);
                break;
        }
    }

    private static void makeDefencesMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        ImageView targetView = getImageView("Target.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(infoView, targetView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
        backView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeStartingMenu(group,primaryStage);
            }
        });
        infoView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeDefencesInfoMenu(group, cell);
            }
        });
        targetView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeDefencesAttackInfoMenu(group, cell);
            }
        });
    }

    private static void makeDefencesInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        File overallInfoFile=new File(ADDRESS+"OverAllInfo.png");
        Image overallInfoImage=new Image(overallInfoFile.toURI().toString());
        ImageView overallInfoView=new ImageView(overallInfoImage);
        File upgradeInfoFile=new File(ADDRESS+"UpgradeInfo.png");
        Image upgradeInfoImage=new Image(upgradeInfoFile.toURI().toString());
        ImageView upgradeInfoView=new ImageView(upgradeInfoImage);
        File attackInfoFile=new File(ADDRESS+"Attack Info.png");
        Image attackInfoImage=new Image(attackInfoFile.toURI().toString());
        ImageView attackInfoView=new ImageView(attackInfoImage);
        File upgradeFile=new File(ADDRESS+"upgrade.png");
        Image upgradeImage=new Image(upgradeFile.toURI().toString());
        ImageView upgradeView=new ImageView(upgradeImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(overallInfoView, upgradeInfoView, attackInfoView, upgradeView, backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);

        backView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //makeSideBar(group);
                //makeDefencesMenu(group, cell);
                makeDefencesMenu(group , cell);
            }
        });
        overallInfoView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //makeSideBar(group);
                makeDefencesOverallInfoMenu(group, cell);
            }
        });
        upgradeInfoView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //makeSideBar(group);
                makeDefencesUpgradeInfoMenu(group, cell);
            }
        });
        attackInfoView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //makeSideBar(group);
                makeDefencesAttackInfoMenu(group, cell);
            }
        });
        upgradeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //makeSideBar(group);
                makeDefencesUpgradeMenu(group, cell);
            }
        });
    }

    private static void makeDefencesUpgradeMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        implementUpgradeBuildings(group, cell);
    }

    private static void makeDefencesAttackInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView attacklInfoInsides= getImageView("attackInfoInsides.png");
        attacklInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        attacklInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(attacklInfoInsides);

        String damage = "";
        if (cell.getClass().getSimpleName().equals("ArcherTower")) {
            damage = "Ground units";
        }
        if (cell.getClass().getSimpleName().equals("AirDefence")) {
            damage = "Flying units";
        }
        if (cell.getClass().getSimpleName().equals("Cannon")) {
            damage = "Ground units";
        }
        if (cell.getClass().getSimpleName().equals("WizardTower")) {
            damage = "Ground & Flying units";
        }
        makeLabels(group,damage,0.25,true);
        makeLabels(group,Integer.toString(cell.getDamage()),0.3,true);
        makeLabels(group,Integer.toString(cell.getRange()),0.35,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            makeDefencesMenu(group , cell);
        });
    }

    private static void makeDefencesUpgradeInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        showUpgradeInfo(group, cell);
    }

    private static void makeDefencesOverallInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        showOverallInfo(group, cell);
    }

    private static void implementBuildATowerCommand(String buildingName, Group group)  {
        Builder builder = null;
        try {
            builder = controller.getGame().getVillage().findFreeBuilder();
        } catch (NotEnoughFreeBuildersException e) {
            group.getChildren().add(e.getImageView());
            return;
        }
        for (Cell cell : Cell.getCellKinds()) {
            if (cell.getClass().getSimpleName().equals(buildingName)) {
                Class spacialBuilding = cell.getClass();
                try {
                    MapUI.setIsInBuildMenu(true);
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_ELIXIR_COST");
                    makeLabels(group,"Do you want to build\n" + buildingName + "\nfor " + goldCost + " gold and " + elixirCost + " elixir?",0.27,false);
                    makeLabels(group,"if yes,\nplease select the spot you want\nto build your building on",0.39,false);
                    ImageView buildView = getImageView("Build.png");
                    buildView.setX(UIConstants.BUTTON_STARTING_X);
                    buildView.setY(Screen.getPrimary().getVisualBounds().getHeight()*0.55);
                    group.getChildren().add(buildView);
                    Builder finalBuilder = builder;
                    buildView.setOnMouseClicked(event -> {
                        controller.getGame().getVillage().getMap()[14][14].getImageView().requestFocus();
                        if (goldCost > controller.getGame().getVillage().getResource().getGold() || elixirCost > controller.getGame().getVillage().getResource().getElixir()) {
                            group.getChildren().add(new NotEnoughResourcesException().getImageView());
                    } else {
                            try {
                                newCell.setY(MapUI.getBuildY());
                                newCell.setX(MapUI.getBuildX());
                                controller.getGame().getVillage().buildTower(newCell);
                                newCell.setWorkingBuilder(finalBuilder);
                                finalBuilder.setOccupationState(true);
                                Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold() - newCell.getGoldCost(), controller.getGame().getVillage().getResource().getElixir() - newCell.getElixirCost());
                                controller.getGame().getVillage().setResource(resource);
                                MapUI.setIsInBuildMenu(false);
                                makeMainBuildingMenu(group);
                            } catch (MarginalTowerException e) {
                                new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                                group.getChildren().add(e.getImageView());
                            } catch (BusyCellException e) {
                                new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                                group.getChildren().add(e.getImageView());
                            }

                    }} );
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void makeLabels(Group group, String message,double yCoefficient,boolean isInfoLabel) {
        Label label=new Label(message);
        if(isInfoLabel) {
            label.relocate(UIConstants.INFO_LABEL_STARTING_X + 10, Screen.getPrimary().getVisualBounds().getHeight() * yCoefficient);
        }else{
            label.relocate(UIConstants.LABELS_STARTING_X + 10, Screen.getPrimary().getVisualBounds().getHeight() * yCoefficient);
        }
        label.setTextFill(Color.BROWN);
        label.setFont(Font.font("Papyrus",16));
        group.getChildren().add(label);
    }

    private static void barrackBuildSoldierMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView archerView = new ImageView(MapUI.getGifsOfTowers().get("ArcherPortrait"));
        archerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        archerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView dragonView = new ImageView(MapUI.getGifsOfTowers().get("DragonPortrait"));
        dragonView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        dragonView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView giantView = new ImageView(MapUI.getGifsOfTowers().get("GiantPortrait"));
        giantView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        giantView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView guardianView = new ImageView(MapUI.getGifsOfTowers().get("GuardianPortrait"));
        guardianView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        guardianView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView wallBreakerView = new ImageView(MapUI.getGifsOfTowers().get("WallBreakerPortrait"));
        wallBreakerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        wallBreakerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView healerView = new ImageView(MapUI.getGifsOfTowers().get("HealerPortrait"));
        healerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        healerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);

        addBuildSoldier(group, cell, archerView, "Archer");
        addBuildSoldier(group, cell, dragonView, "Dragon");
        addBuildSoldier(group, cell, giantView, "Giant");
        addBuildSoldier(group, cell, guardianView, "Guardian");
        addBuildSoldier(group, cell, wallBreakerView, "WallBreaker");
        addBuildSoldier(group, cell, wallBreakerView, "Healer");

        opacityOnHover(archerView);
        opacityOnHover(dragonView);
        opacityOnHover(giantView);
        opacityOnHover(guardianView);
        opacityOnHover(wallBreakerView);
        opacityOnHover(healerView);

        HBox soldiers1 = new HBox(1,archerView, dragonView);
        HBox soldiers2 = new HBox(1,giantView, guardianView);
        HBox soldiers3 = new HBox(1,healerView, wallBreakerView);

        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        backView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeBarrackMenu(group, cell);
            }
        });

        VBox allSoldiers = new VBox(1, soldiers1, soldiers2, soldiers3, backView);
        allSoldiers.relocate(50, 160);

        group.getChildren().addAll(allSoldiers);
    }

    public static void opacityOnHover(ImageView imageView) {
        imageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setOpacity(0.6);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setOpacity(1);
            }
        });
    }

    private static void addBuildSoldier(Group group, Cell cell, ImageView imageView, String name) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    implementBuildSoldier(group, cell, name);
                    barrackBuildSoldierMenu(group, cell);
                } catch (NotEnoughResourcesException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                } catch (NotEnoughCapacityInCampsException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                } catch (unAvailableSoldierException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                }
            }
        });
    }

    private static void implementBuildSoldier(Group group, Cell cell, String playerChoice) throws NotEnoughResourcesException, NotEnoughCapacityInCampsException, unAvailableSoldierException {
        Barrack barrack = (Barrack) cell;
        HashMap<String, Integer> availableSoldiers = barrack.determineAvailableSoldiers(controller.getGame().getVillage().getResource().getElixir());
        if (availableSoldiers.get(playerChoice) == 0) {
            throw new unAvailableSoldierException();
        } else {
            int soldierAmount = 1;
            int totalCapacity = 0;
            for (Camp camp : controller.getGame().getVillage().getCamps()) {
                if (!camp.getUnderConstructionStatus())
                    totalCapacity += camp.getCapacity() - camp.getSoldiers().size();
            }
            if (soldierAmount > totalCapacity) {
                throw new NotEnoughCapacityInCampsException();
            }
            barrack.buildSoldier(soldierAmount, playerChoice, availableSoldiers);
            Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir() - soldierAmount * Config.getDictionary().get(playerChoice + "_ELEXIR_COST"));
            controller.getGame().getVillage().setResource(resource);
        }
    }
}
