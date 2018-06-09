package com.company.UIs;

import javafx.scene.Group;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class SideBarUI {
    public static void makeSideBar(Stage primaryStage,Group group){
        File sideBarFile=new File(".\\src\\com\\company\\UIs\\SideBarMenuImages\\labelLessCroppedMenu.png");
        Image sideBarMenuBackground=new Image(sideBarFile.toURI().toString());
        ImageView sideBarBackgroundImageView=new ImageView(sideBarMenuBackground);
        Double sideBarStartingX=-sideBarMenuBackground.getWidth()/16+Screen.getPrimary().getVisualBounds().getWidth()-Screen.getPrimary().getVisualBounds().getHeight()-sideBarBackgroundImageView.getImage().getWidth();
        File borderFile=new File(".\\src\\com\\company\\UIs\\SideBarMenuImages\\upperBorder.png");
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
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
    }
}
