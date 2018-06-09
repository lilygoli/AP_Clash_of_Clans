package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Cell;
import javafx.event.EventHandler;
import javafx.scene.Group;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.io.File;

public class SideBarUI {
    private static final String ADDRESS  = "./src/com/company/UIs/SideBarMenuImages/";
    private static Controller controller;

    public static void setController(Controller controller) {
        SideBarUI.controller = controller;
    }

    public static void makeSideBar(Group group){
        File sideBarFile=new File("./src/com/company/UIs/SideBarMenuImages/labelLessCroppedMenu.png");
        Image sideBarMenuBackground=new Image(sideBarFile.toURI().toString());
        ImageView sideBarBackgroundImageView=new ImageView(sideBarMenuBackground);
        Double sideBarStartingX=-sideBarMenuBackground.getWidth()/16;
        File borderFile=new File("./src/com/company/UIs/SideBarMenuImages/upperBorder.png");
        Image borderImage=new Image(borderFile.toURI().toString());
        ImageView borderImageView=new ImageView(borderImage);
        borderImageView.setScaleX(0.6);
        borderImageView.setScaleY(0.8);
        borderImageView.setY(40);
        borderImageView.setX(sideBarStartingX+20);
        sideBarBackgroundImageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        sideBarBackgroundImageView.setScaleY(0.95);
        sideBarBackgroundImageView.setX(sideBarStartingX);
        sideBarBackgroundImageView.setY(-20);
        File saveFile=new File(ADDRESS+"save.png");
        Image saveImage=new Image(saveFile.toURI().toString());
        ImageView saveView=new ImageView(saveImage);
        saveView.setX(sideBarStartingX+30);
        saveView.setY(Screen.getPrimary().getVisualBounds().getHeight()*6/10);
        saveView.setScaleX(0.5);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        group.getChildren().add(saveView);
    }
    public static void makeMainBuildingMenu(Group group){
            File infoFile=new File(ADDRESS+"info.png");
            Image infoImage=new Image(infoFile.toURI().toString());
            ImageView infoView=new ImageView(infoImage);
            File availableBuildingsFile=new File(ADDRESS+"AvailableBuildings.png");
            Image availableBuildingsImage=new Image(availableBuildingsFile.toURI().toString());
            ImageView availableBuildingView=new ImageView(availableBuildingsImage);
            availableBuildingView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    makeSideBar(group);
                    ComboBox<String > comboBox= new ComboBox<>();
                    String availableBuildings = controller.getGame().getVillage().getMainBuilding().findAvailableBuildings(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir());
                    comboBox.getItems().addAll(availableBuildings.split("\n"));
                    comboBox.relocate(80,160);
                    group.getChildren().add(comboBox);
                    File backFile=new File(ADDRESS+"back.png");
                    Image backImage=new Image(backFile.toURI().toString());
                    ImageView backView=new ImageView(backImage);
                    backView.setScaleX(0.5);
                    backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*0.5);
                    backView.setX(55);
                    group.getChildren().add(backView);

                }
            });
            File statusFile=new File(ADDRESS+"Status.png");
            Image statusImage=new Image(statusFile.toURI().toString());
            ImageView statusView=new ImageView(statusImage);
            File backFile=new File(ADDRESS+"Back.png");
            Image backImage=new Image(backFile.toURI().toString());
            ImageView backView=new ImageView(backImage);
            VBox vBox=new VBox(infoView,availableBuildingView,statusView,backView);
            vBox.relocate(50,160);
            group.getChildren().add(vBox);
    }
    public static void makeStorageMenu(Group group , Cell cell){
        File overAllInfoFile=new File(ADDRESS+"OverAllInfo.png");
        Image overAllInfoImage=new Image(overAllInfoFile.toURI().toString());
        ImageView overAllInfoView=new ImageView(overAllInfoImage);
        File UpgradeInfoFile=new File(ADDRESS+"UpgradeInfo.png");
        Image UpgradeInfoImage=new Image(UpgradeInfoFile.toURI().toString());
        ImageView UpgradeInfoView=new ImageView(UpgradeInfoImage);
        File SourcesInfoFile=new File(ADDRESS+"SourcesInfo.png");
        Image SourcesInfoImage=new Image(SourcesInfoFile.toURI().toString());
        ImageView SourcesInfoView=new ImageView(SourcesInfoImage);
        File upgradeFile=new File(ADDRESS+"upgrade.png");
        Image upgradeImage=new Image(upgradeFile.toURI().toString());
        ImageView upgradeView=new ImageView(upgradeImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,overAllInfoView,UpgradeInfoView,SourcesInfoView,upgradeView ,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeCampMenu(Group group , Cell cell){
        File infoFile=new File(ADDRESS+"info.png");
        Image infoImage=new Image(infoFile.toURI().toString());
        ImageView infoView=new ImageView(infoImage);
        File SoldiersFile=new File(ADDRESS+"Soldiers.png");
        Image SoldiersImage=new Image(SoldiersFile.toURI().toString());
        ImageView SoldiersView=new ImageView(SoldiersImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,infoView,SoldiersView,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeCampInfoMenu(Group group , Cell cell){
        File overAllInfoFile=new File(ADDRESS+"OverAllInfo.png");
        Image overAllInfoImage=new Image(overAllInfoFile.toURI().toString());
        ImageView overAllInfoView=new ImageView(overAllInfoImage);
        File UpgradeInfoFile=new File(ADDRESS+"UpgradeInfo.png");
        Image UpgradeInfoImage=new Image(UpgradeInfoFile.toURI().toString());
        ImageView UpgradeInfoView=new ImageView(UpgradeInfoImage);
        File CapacityInfoFile=new File(ADDRESS+"CapacityInfo.png");
        Image CapacityInfoImage=new Image(CapacityInfoFile.toURI().toString());
        ImageView CapacityInfoView=new ImageView(CapacityInfoImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,overAllInfoView,UpgradeInfoView,CapacityInfoView,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeMineMenu(Group group , Cell cell){
        File infoFile=new File(ADDRESS+"info.png");
        Image infoImage=new Image(infoFile.toURI().toString());
        ImageView infoView=new ImageView(infoImage);
        File MineFile=new File(ADDRESS+"Mine.png");
        Image MineImage=new Image(MineFile.toURI().toString());
        ImageView MineView=new ImageView(MineImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,infoView,MineView,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeMineAndBarracksInfoMenu(Group group , Cell cell){
        File overAllInfoFile=new File(ADDRESS+"OverAllInfo.png");
        Image overAllInfoImage=new Image(overAllInfoFile.toURI().toString());
        ImageView overAllInfoView=new ImageView(overAllInfoImage);
        File UpgradeInfoFile=new File(ADDRESS+"UpgradeInfo.png");
        Image UpgradeInfoImage=new Image(UpgradeInfoFile.toURI().toString());
        ImageView UpgradeInfoView=new ImageView(UpgradeInfoImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,overAllInfoView,UpgradeInfoView,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeBarrackMenu(Group group , Cell cell){
        File infoFile=new File(ADDRESS+"info.png");
        Image infoImage=new Image(infoFile.toURI().toString());
        ImageView infoView=new ImageView(infoImage);
        File BuildSoldiersFile=new File(ADDRESS+"BuildSoldiers.png");
        Image BuildSoldiersImage=new Image(BuildSoldiersFile.toURI().toString());
        ImageView BuildSoldiersView=new ImageView(BuildSoldiersImage);
        File statusFile=new File(ADDRESS+"Status.png");
        Image statusImage=new Image(statusFile.toURI().toString());
        ImageView statusView=new ImageView(statusImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(1,infoView,BuildSoldiersView,statusView,backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }

    public static void makeBuildingMenu(Group group, Cell cell) {
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
        File infoFile=new File(ADDRESS+"info.png");
        Image infoImage=new Image(infoFile.toURI().toString());
        ImageView infoView=new ImageView(infoImage);
        File target=new File(ADDRESS+"Target.png");
        Image targetImage=new Image(target.toURI().toString());
        ImageView targetView=new ImageView(targetImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(infoView, targetView, backView);
        vBox.relocate(50,160);
        group.getChildren().add(vBox);
    }
}
