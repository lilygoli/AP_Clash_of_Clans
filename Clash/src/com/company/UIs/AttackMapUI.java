package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static com.company.UIs.MapUI.getImageOfBuildings;
import static com.company.UIs.MapUI.putBuildingImageInMap;

public class AttackMapUI {
    private static int attackX, attackY;
    private static PannableCanvas canvas = new PannableCanvas();
    private static Controller controller;
    private static AnimationTimer showMapAnimationTimer;
    private static Stage primaryStage;

    public static void makeAttackGameBoard( Stage stage, Controller controller) throws FileNotFoundException {
        AttackMapUI.controller=controller;
        primaryStage=stage;
        Group root= new Group();
        Scene scene= new Scene(root,Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        Random random = new Random();
        boolean flag;
        FileInputStream fileInputStream;
        for (int i = 33; i >= 0; i--) {
            for (int j = 0; j < 32; j++) {
                flag = random.nextBoolean();
                fileInputStream = makeGrasses(flag, i, j);
                Image image = new Image(fileInputStream);
                ImageView imageView = new ImageView(image);
                imageView.relocate(scene.getWidth() - ((i + 1) * Screen.getPrimary().getVisualBounds().getHeight() / 32) , j * Screen.getPrimary().getVisualBounds().getHeight() / 32);
                imageView.setFitHeight(scene.getHeight() / 32);
                imageView.setFitWidth(scene.getHeight() / 32);
                canvas.getChildren().add(imageView);

                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked(event -> {
                        attackX = finalI - 2;
                        attackY = finalJ - 1;
                        imageView.requestFocus();
                });
            }

        }
        showMapInAttack(root);
    }

    public static void showMapInAttack(Group root) {
        Village village = controller.getGame().getAttackedVillage().getVillage();

        showMapAnimationTimer=new AnimationTimer(){

            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000) {
                    int flag = 0;
                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < 30; j++) {
                            if (village.getMap()[j][i].getClass() == Grass.class) {
                                continue;
                            } else if (village.getMap()[j][i].getClass() == MainBuilding.class) {
                                if (flag == 0) {
                                    flag = 1;
                                    village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        MapUI.setOnClickImages(14, 14, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j, village,16);
                                }

                            } else {
                                if(village.getMap()[j][i].getUnderConstructionStatus()){
                                    village.getMap()[j][i].setImage( getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".gif"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        MapUI.setOnClickImages(i, j, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j, village,32);
                                }else {
                                    village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        MapUI.setOnClickImages(i, j, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j, village,32);
                                }
                            }
                        }
                    }
                    lastUpdate = now ;
                }
            }
        };
        showMapAnimationTimer.start();
    }

    public static FileInputStream makeGrasses(boolean flag, int i, int j) throws FileNotFoundException {
        FileInputStream fileInputStream;
        if (i <= 1 || i >= 32 || j == 0 || j == 31){
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/Brown.png");
        }
        else if (flag){
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/darkGreen.png");
        }
        else{
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/lightGreen.png");
        }
        return fileInputStream;
    }
}
