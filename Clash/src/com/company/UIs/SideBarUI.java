package com.company.UIs;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SideBarUI {
    public void makeSideBar(Stage primaryStage,Group group){
        Image sideBarMenuBackground=new Image("labelLessCroppedMenu.png");
        ImageView sideBarBackgroundImageView=new ImageView(sideBarMenuBackground);
        Image borderImage=new Image("upperBorder2.png");
        ImageView borderImageView=new ImageView(borderImage);
        borderImageView.setScaleX(0.6);
        borderImageView.setScaleY(0.8);
        borderImageView.setY(30);
        borderImageView.setX(10);
        sideBarBackgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        sideBarBackgroundImageView.setScaleY(0.95);
        Circle circle=new Circle(260,50,2);
        sideBarBackgroundImageView.setX(-sideBarMenuBackground.getWidth()/16+Screen.getPrimary().getVisualBounds().getWidth()-Screen.getPrimary().getVisualBounds().getHeight());
        sideBarBackgroundImageView.setY(-20);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        group.getChildren().add(circle);
    }
}
