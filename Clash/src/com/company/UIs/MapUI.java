package com.company.UIs;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MapUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);

        File file = new File("./src/com/company/UIs/MapResources/MapBackGround.jpg");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        backGroundView.setOpacity(0.7);
        root.getChildren().add(backGroundView);

        makeGameBoard(root, scene);

        file = new File("./src/com/company/UIs/MapResources/mapBorder.png");
        Image mapBorder = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView mapBorderView = new ImageView(mapBorder);
        mapBorderView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 1.4);
        mapBorderView.relocate(Screen.getPrimary().getVisualBounds().getWidth() - Screen.getPrimary().getVisualBounds().getHeight() * 1.09, -Screen.getPrimary().getVisualBounds().getHeight() / 5);
        root.getChildren().add(mapBorderView);
    }

    private void makeGameBoard(Group root, Scene scene) throws FileNotFoundException {
        Random random = new Random();
        boolean flag;
        FileInputStream fileInputStream;
        for (int i = 33; i >= 0; i--) {
            for (int j = 0; j < 32; j++) {
                flag = random.nextBoolean();
                if (i <= 1 || i >= 32 || j == 0 || j == 31){
                    fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/Brown.png");
                }
                else if (flag){
                    fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/darkGreen.png");
                }
                else{
                    fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/lightGreen.png");
                }
                    Image image = new Image(fileInputStream);
                    ImageView imageView = new ImageView(image);
                    imageView.relocate(scene.getWidth() - ((i + 1) * scene.getHeight() / 32) , j * scene.getHeight() / 32);
                    imageView.setFitHeight(scene.getHeight() / 32);
                    imageView.setFitWidth(scene.getHeight() / 32);
                    root.getChildren().add(imageView);
            }
        }
    }
}
