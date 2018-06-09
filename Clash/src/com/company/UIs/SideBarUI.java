package com.company.UIs;

import javafx.scene.Group;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class SideBarUI {
    private static final String ADDRESS  = ".\\src\\com\\company\\UIs\\SideBarMenuImages\\";
    public static void makeSideBar(Stage primaryStage,Group group){
        File sideBarFile=new File(ADDRESS+"labelLessCroppedMenu.png");
        Image sideBarMenuBackground=new Image(sideBarFile.toURI().toString());
        ImageView sideBarBackgroundImageView=new ImageView(sideBarMenuBackground);
        Double sideBarStartingX=-sideBarMenuBackground.getWidth()/16;
        File borderFile=new File(ADDRESS+"upperBorder.png");
        Image borderImage=new Image(borderFile.toURI().toString());
        ImageView borderImageView=new ImageView(borderImage);
        borderImageView.setScaleX(0.6);
        borderImageView.setScaleY(0.8);
        borderImageView.setY(40);
        borderImageView.setX(sideBarStartingX+20);
        sideBarBackgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        sideBarBackgroundImageView.setScaleY(0.95);
        sideBarBackgroundImageView.setX(sideBarStartingX);
        sideBarBackgroundImageView.setY(-20);
        File saveFile=new File(ADDRESS+"save.png");
        Image saveImage=new Image(saveFile.toURI().toString());
        ImageView saveView=new ImageView(saveImage);
        saveView.setX(sideBarStartingX+30);
        saveView.setY(Screen.getPrimary().getVisualBounds().getHeight()-250);
        saveView.setScaleX(0.5);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        group.getChildren().add(saveView);
    }
    public void makeMainBuildingMenu(Group group){
        File infoFile=new File(ADDRESS+"info.png");
        Image infoImage=new Image(infoFile.toURI().toString());
        ImageView infoView=new ImageView(infoImage);
        File availableBuildingsFile=new File(ADDRESS+"AvailableBuildings.png");
        Image availableBuildingsImage=new Image(availableBuildingsFile.toURI().toString());
        ImageView availableBuildingView=new ImageView(availableBuildingsImage);
        File statusFile=new File(ADDRESS+"Status.png");
        Image statusImage=new Image(statusFile.toURI().toString());
        ImageView statusView=new ImageView(statusImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        VBox vBox=new VBox(infoView,availableBuildingView,statusView,backView);
        group.getChildren().add(vBox);
    }
}
