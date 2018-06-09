package com.company.UIs;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class MapUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        Random random = new Random();
        boolean flag;
        FileInputStream fileInputStream;
        for (int i = 31; i >= 0; i--) {
            for (int j = 0; j < 32; j++) {
                flag = random.nextBoolean();
                if (i == 0 || i == 31 || j == 0 || j == 31){
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
