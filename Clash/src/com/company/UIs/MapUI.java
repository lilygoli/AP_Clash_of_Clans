package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MapUI extends Application {
    private static int buildX;
    private static int buildY;

    private Controller controller = new Controller();

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        PannableCanvas canvas = new PannableCanvas();


        File file = new File("./src/com/company/UIs/MapResources/MapBackGround.jpg");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        backGroundView.setOpacity(0.7);
        root.getChildren().add(backGroundView);

        makeGameBoard(root ,scene , canvas);

//        file = new File("./src/com/company/UIs/MapResources/mapBorder.png");
//        Image mapBorder = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
//        ImageView mapBorderView = new ImageView(mapBorder);
//        mapBorderView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 1.2);
//        mapBorderView.relocate(Screen.getPrimary().getVisualBounds().getWidth() - Screen.getPrimary().getVisualBounds().getHeight() * 1.09, -Screen.getPrimary().getVisualBounds().getHeight() / 5.9);
//        canvas.getChildren().add(mapBorderView);
//        SideBarUI.makeSideBar(primaryStage , root);
//        mapBorderView.setScaleY(0.9);
//        mapBorderView.setScaleX(0.93);
//        mapBorderView.relocate(Screen.getPrimary().getVisualBounds().getWidth() - Screen.getPrimary().getVisualBounds().getHeight() * 1.15, -Screen.getPrimary().getVisualBounds().getHeight() / 5);

        showMap(controller.getGame().getVillage(),canvas, root);

        root.getChildren().add(canvas);
        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        SideBarUI.setController(controller);
        SideBarUI.makeSideBar(root);
    }


    private void makeGameBoard(Group root , Scene scene , PannableCanvas canvas) throws FileNotFoundException {
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
                    double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
                    fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/darkGreen.png");
                }
                else{
                    fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/lightGreen.png");
                }
                    Image image = new Image(fileInputStream);
                    ImageView imageView = new ImageView(image);
                    imageView.relocate(scene.getWidth() - ((i + 1) * Screen.getPrimary().getVisualBounds().getHeight() / 32) , j * Screen.getPrimary().getVisualBounds().getHeight() / 32);
                    imageView.setFitHeight(scene.getHeight() / 32);
                    imageView.setFitWidth(scene.getHeight() / 32);
                    canvas.getChildren().add(imageView);
                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            buildX = 31 - finalI;
                            buildY = finalJ - 1;
                        }
                    });
            }
        }
        showMap(controller.getGame().getVillage(),canvas, root);
    }
    public void showMap(Village village,PannableCanvas canvas, Group root) {
        int flag=0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (village.getMap()[j][i].getClass() == Grass.class) {
                    continue;
                }else if (village.getMap()[j][i].getClass() == MainBuilding.class) {
                    if(flag==0) {
                        flag = 1;
                        ImageView imageView = getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName());
                        imageView.setX(mapCoordinates2PixelX(i));
                        imageView.setY(mapCoordinates2PixelY(j));
                        imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / 16);
                        imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / 16);
                        village.getMap()[j][i].setImage(imageView);
                        int finalI = i;
                        int finalJ = j;
                        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                SideBarUI.makeBuildingMenu(root, village.getMap()[finalJ][finalI]);
                            }
                        });
                        canvas.getChildren().add(imageView);
                    }

                } else {
                    ImageView imageView=getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName());
                    imageView.setX(mapCoordinates2PixelX(i));
                    imageView.setY(mapCoordinates2PixelY(j));
                    village.getMap()[j][i].setImage(imageView);
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            SideBarUI.makeBuildingMenu(root, village.getMap()[finalJ][finalI]);
                        }
                    });
                    canvas.getChildren().add(imageView);
                }
            }
        }

    }
    private ImageView getImageOfBuildings(String name){
        File file=new File("./src/com/company/ImagesAndGifs/Buildings/"+name+".png");
        Image buildingImage = new Image(file.toURI().toString());
        ImageView imageView= new ImageView(buildingImage);
        imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / 32);
        imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / 32);
        return  imageView;
    }
    private double mapCoordinates2PixelX(int x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return Screen.getPrimary().getVisualBounds().getWidth() - (x + 3) * cellWidth;
    }

    private double mapCoordinates2PixelY(int y) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return (y + 1) * cellWidth;
    }

    private int pixel2MapCoordinatesX(double x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        x = x - Screen.getPrimary().getVisualBounds().getWidth();
        x = - x / cellWidth;
        return (int)(x - 3);
    }

    private int pixel2MapCoordinatesY(double y) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return (int) (y / cellWidth - 1);
    }
}
