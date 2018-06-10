package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Exception.BusyCellException;
import com.company.Exception.MarginalTowerException;
import com.company.Exception.NotEnoughFreeBuildersException;
import com.company.Exception.NotEnoughResourcesException;
import com.company.Models.Builder;
import com.company.Models.Config;
import com.company.Models.Resource;
import com.company.Models.Towers.Cell;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class SideBarUI {
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static Controller controller;
    private static ImageView menuBackground=new ImageView();

    public static void setController(Controller controller) {
        SideBarUI.controller = controller;
    }

    public static void makeSideBar(Group group) {
        File sideBarFile = new File("./src/com/company/UIs/SideBarMenuImages/labelLessCroppedMenu.png");
        Image sideBarMenuBackground = new Image(sideBarFile.toURI().toString());
        ImageView sideBarBackgroundImageView = new ImageView(sideBarMenuBackground);
        Double sideBarStartingX = -sideBarMenuBackground.getWidth() / 16;
        sideBarBackgroundImageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        sideBarBackgroundImageView.setScaleY(0.95);
        sideBarBackgroundImageView.setX(sideBarStartingX);
        sideBarBackgroundImageView.setY(-20);
        menuBackground=sideBarBackgroundImageView;
        File borderFile = new File("./src/com/company/UIs/SideBarMenuImages/upperBorder.png");
        Image borderImage = new Image(borderFile.toURI().toString());
        ImageView borderImageView = new ImageView(borderImage);
        borderImageView.setScaleX(0.6);
        borderImageView.setScaleY(0.8);
        borderImageView.setY(40);
        borderImageView.setX(sideBarStartingX + 20);
        ImageView saveView = getImageView("save.png");
        saveView.setX(sideBarStartingX + 30);
        saveView.setY(Screen.getPrimary().getVisualBounds().getHeight() * 6 / 10);
        saveView.setScaleX(0.5);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        group.getChildren().add(saveView);
    }

    public static void makeMainBuildingMenu(Group group) {
        makeSideBar(group);
        ImageView infoView = getImageView("info.png");
        ImageView availableBuildingView = getImageView("AvailableBuildings.png");
        availableBuildingView.setOnMouseClicked(mouseEvent -> {
            showAvailableBuildings(group);
        });
        ImageView statusView = getImageView("Status.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(infoView, availableBuildingView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static ImageView getImageView(String imageName) {
        File infoFile = new File(ADDRESS + imageName);
        Image infoImage = new Image(infoFile.toURI().toString());
        return new ImageView(infoImage);
    }

    private static void showAvailableBuildings(Group group) {
        makeSideBar(group);
        makeLabels(group,"choose your preferred building",0.19);
        ComboBox<String> comboBox = new ComboBox<>();
        String availableBuildings = controller.getGame().getVillage().getMainBuilding().findAvailableBuildings(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir());
        comboBox.getItems().addAll(availableBuildings.split("\n"));
        comboBox.relocate(70, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(comboBox);
        Button selectButton=new Button("select");
        selectButton.relocate(200,UIConstants.MENU_VBOX_STARTING_Y);
        selectButton.setOnMouseClicked(event -> {
            implementBuildATowerCommand(comboBox.getValue(),group);
        });
        group.getChildren().add(selectButton);
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event -> {
            makeMainBuildingMenu(group);
        });
        group.getChildren().add(backView);
    }

    public static void makeStorageMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        ImageView SourcesInfoView = getImageView("SourcesInfo.png");
        ImageView upgradeView = getImageView("upgrade.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, SourcesInfoView, upgradeView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeCampMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView infoView = getImageView("info.png");
        ImageView SoldiersView = getImageView("Soldiers.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, infoView, SoldiersView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeCampInfoMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        ImageView CapacityInfoView = getImageView("CapacityInfo.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, CapacityInfoView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeMineMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView infoView = getImageView("info.png");
        ImageView MineView = getImageView("Mine.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, infoView, MineView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeMineAndBarracksInfoMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeBarrackMenu(Group group, Cell cell) {
        makeSideBar(group);
        ImageView infoView = getImageView("info.png");
        ImageView BuildSoldiersView = getImageView("BuildSoldiers.png");
        ImageView statusView = getImageView("Status.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(1, infoView, BuildSoldiersView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void makeBuildingsMenu(Group group, Cell cell) {
        makeSideBar(group);
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
            case "GoldStorage":
            case "ElixirStorage":
                makeStorageMenu(group, cell);
                break;
        }
    }

    private static void makeDefencesMenu(Group group, Cell cell) {
        ImageView infoView = getImageView("info.png");
        ImageView targetView = getImageView("Target.png");
        ImageView backView = getImageView("Back.png");
        VBox vBox = new VBox(infoView, targetView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static void implementBuildATowerCommand(String buildingName,Group group)  {
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
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_ELIXIR_COST");
                    makeLabels(group,"Do you want to build\n" + buildingName + "\nfor " + goldCost + " gold and " + elixirCost + " elixir?",0.27);
                    makeLabels(group,"if yes,\nplease select the spot you want\nto build your building on",0.35);
                    ImageView buildView = getImageView("Build.png");
                    buildView.setX(UIConstants.BUTTON_STARTING_X);
                    buildView.setY(Screen.getPrimary().getVisualBounds().getHeight()*0.55);
                    group.getChildren().add(buildView);
                    Builder finalBuilder = builder;
                    buildView.setOnMouseClicked(event -> {
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

    private static void makeLabels(Group group, String message,double yCoefficient) {
        Label locationLabel=new Label(message);
        locationLabel.relocate(UIConstants.LABELS_STARTING_X, Screen.getPrimary().getVisualBounds().getHeight()*yCoefficient);
        locationLabel.setFont(Font.font("Vivaldi",20));
        group.getChildren().add(locationLabel);
    }

}
