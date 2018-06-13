package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;

public class MapUI  {
    private static int buildX;
    private static int buildY;
    private static PannableCanvas canvas = new PannableCanvas();
    private static HashMap<String ,Image> gifsOfTowers=new HashMap<>();
    private static boolean isInBuildMenu=false;
    private static AnimationTimer showMapAnimationTimer;

    public static void setIsInBuildMenu(boolean isInBuildMenu) {
        MapUI.isInBuildMenu=isInBuildMenu;
    }

    public static HashMap<String, Image> getGifsOfTowers() {
        return gifsOfTowers;
    }

    private static Controller controller = new Controller();
// to be moved to a new thread
// TODO: 6/11/2018 giant kill gif add and healer and wallbreaker and guardian giant kill add

    static {
        File AirDefenceFile = new File("./src/com/company/ImagesAndGifs/Buildings/AirDefenceLoading.gif");
        gifsOfTowers.put("AirDefence", new Image(AirDefenceFile.toURI().toString()));
        File ArcherTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/ArcherTowerLoading.gif");
        gifsOfTowers.put("ArcherTower", new Image(ArcherTowerFile.toURI().toString()));
        File CampFile = new File("./src/com/company/ImagesAndGifs/Buildings/CampLoading.gif");
        gifsOfTowers.put("Camp", new Image(CampFile.toURI().toString()));
        File CannonFile = new File("./src/com/company/ImagesAndGifs/Buildings/CannonLoading.gif");
        gifsOfTowers.put("Cannon", new Image(CannonFile.toURI().toString()));
        File ElixirMineFile = new File("./src/com/company/ImagesAndGifs/Buildings/ElixirMineLoading.gif");
        gifsOfTowers.put("ElixirMine", new Image(ElixirMineFile.toURI().toString()));
        File ElixirStorageFile = new File("./src/com/company/ImagesAndGifs/Buildings/ElixirStorageLoading.gif");
        gifsOfTowers.put("ElixirStorage", new Image(ElixirStorageFile.toURI().toString()));
        File GoldMineFile = new File("./src/com/company/ImagesAndGifs/Buildings/GoldMineLoading.gif");
        gifsOfTowers.put("GoldMine", new Image(GoldMineFile.toURI().toString()));
        File GoldStorageFile = new File("./src/com/company/ImagesAndGifs/Buildings/GoldStorageLoading.gif");
        gifsOfTowers.put("GoldStorage", new Image(GoldStorageFile.toURI().toString()));
        File WallFile = new File("./src/com/company/ImagesAndGifs/Buildings/WallLoading.gif");
        gifsOfTowers.put("Wall", new Image(WallFile.toURI().toString()));
        File WizardTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/WizardTowerLoading.gif");
        gifsOfTowers.put("WizardTower", new Image(WizardTowerFile.toURI().toString()));
        File BarrackFile = new File("./src/com/company/ImagesAndGifs/Buildings/BarrackLoading.gif");
        gifsOfTowers.put("Barrack", new Image(BarrackFile.toURI().toString()));
        File archerFile = new File("./src/com/company/ImagesAndGifs/Soldiers/Archer/ArcherPortrait.jpg");
        gifsOfTowers.put("ArcherPortrait", new Image(archerFile.toURI().toString()));
        File dragonFile=new File("./src/com/company/ImagesAndGifs/Soldiers/Dragon/DragonPortrait.jpg");
        gifsOfTowers.put("DragonPortrait", new Image(dragonFile.toURI().toString()));
        File giantFile=new File("./src/com/company/ImagesAndGifs/Soldiers/Giant/GiantPortrait.jpg");
        gifsOfTowers.put("GiantPortrait", new Image(giantFile.toURI().toString()));
        File guardianFile=new File("./src/com/company/ImagesAndGifs/Soldiers/Guardian/GuardianPortrait.jpg");
        gifsOfTowers.put("GuardianPortrait", new Image(guardianFile.toURI().toString()));
        File healerFile=new File("./src/com/company/ImagesAndGifs/Soldiers/Healer/HealerPortrait.jpg");
        gifsOfTowers.put("HealerPortrait", new Image(healerFile.toURI().toString()));
        File wallBreakerFile=new File("./src/com/company/ImagesAndGifs/Soldiers/WallBreaker/WallBreakerPortrait.jpg");
        gifsOfTowers.put("WallBreakerPortrait", new Image(wallBreakerFile.toURI().toString()));
    }

    public static int getBuildX() {
        return buildX;
    }

    public static AnimationTimer getShowMapAnimationTimer() {
        return showMapAnimationTimer;
    }

    public static PannableCanvas getCanvas() {
        return canvas;
    }

    public static int getBuildY() {
        return buildY;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public static void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);

        File file = new File("./src/com/company/UIs/MapResources/MapBackGround.jpg");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        backGroundView.setOpacity(0.7);
        root.getChildren().add(backGroundView);

        makeGameBoard(root ,scene);

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

        showMapInVillage(controller.getGame().getVillage(), root);

        root.getChildren().add(canvas);
        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        SideBarUI.setController(controller);
        makeSlider(root);
        SideBarUI.makeStartingMenu(root,primaryStage);
    }

    private static void makeSlider(Group root) {
        int maxDeltaT = UIConstants.DELTA_T;
        final Slider deltaTSlider = new Slider(0, maxDeltaT, maxDeltaT);
        final Label caption = new Label("Delta T");
        final Label deltaTValue = new Label(Double.toString(deltaTSlider.getValue()));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(20);

        GridPane.setConstraints(caption, 0, 1);
        grid.getChildren().add(caption);


        deltaTSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                deltaTValue.setText(String.format("%d", (int) Math.floor((Double) new_val)));
                UIConstants.DELTA_T = (int) Math.floor((Double) new_val);
            }
        });

        GridPane.setConstraints(deltaTSlider, 1, 1);
        grid.getChildren().add(deltaTSlider);


        GridPane.setConstraints(deltaTValue, 2, 1);
        grid.getChildren().add(deltaTValue);

        grid.relocate(Screen.getPrimary().getVisualBounds().getWidth() * 0.17, Screen.getPrimary().getVisualBounds().getHeight() * 0.88);

        root.getChildren().add(grid);
    }


    private static void makeGameBoard(Group root , Scene scene) throws FileNotFoundException {
        Random random = new Random();
        boolean flag;
        FileInputStream fileInputStream;
        for (int i = 33; i >= 0; i--) {
            for (int j = 0; j < 32; j++) {
                flag = random.nextBoolean();
                fileInputStream = AttackMapUI.makeGrasses(flag, i, j);
                Image image = new Image(fileInputStream);
                ImageView imageView = new ImageView(image);
                imageView.relocate(scene.getWidth() - ((i + 1) * Screen.getPrimary().getVisualBounds().getHeight() / 32) , j * Screen.getPrimary().getVisualBounds().getHeight() / 32);
                imageView.setFitHeight(scene.getHeight() / 32);
                imageView.setFitWidth(scene.getHeight() / 32);
                canvas.getChildren().add(imageView);

                if (!(i <= 1 || i >= 32 || j == 0 || j == 31)) {
                    imageView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    {

                        if (newValue) {
                                try {
                                    FileInputStream fileInputStream2 = new FileInputStream("./src/com/company/ImagesAndGifs/red.png");
                                    imageView.setImage(new Image(fileInputStream2));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                        } else {
                            try {
                                FileInputStream fileInputStream2 = new FileInputStream("./src/com/company/ImagesAndGifs/lightGreen.png");
                                imageView.setImage(new Image(fileInputStream2));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }
                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked(event -> {
                    if(isInBuildMenu) {
                        buildX = finalI - 2;
                        buildY = finalJ - 1;
                        imageView.requestFocus();
                    }
                });
            }

        }
    }
    public static void showMapInVillage(Village village, Group root) {

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
                                        setOnClickImages(14, 14, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j+1, village,16,canvas);
                                }

                            } else {
                                if(village.getMap()[j][i].getUnderConstructionStatus()){
                                    village.getMap()[j][i].setImage( getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".gif"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        setOnClickImages(i, j, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j, village,32, canvas);
                                }else {
                                    village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        setOnClickImages(i, j, root);
                                        village.getMap()[j][i].setIsEventSet(true);
                                    }
                                    putBuildingImageInMap(i, j, village,32,canvas);
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

    public static void putBuildingImageInMap(int i, int j, Village village, int size, PannableCanvas canvas) {
        village.getMap()[j][i].getImageView().setX(mapCoordinates2PixelX(j));
        village.getMap()[j][i].getImageView().setY(mapCoordinates2PixelY(i));
        addGlowToBuildings(village.getMap()[j][i].getImageView());
        village.getMap()[j][i].getImageView().setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / size);
        village.getMap()[j][i].getImageView().setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / size);

            if (canvas.getChildren().contains(village.getMap()[j][i].getImageView())) {
                canvas.getChildren().remove(village.getMap()[j][i].getImageView());
            }
            canvas.getChildren().add(village.getMap()[j][i].getImageView());
    }


    public static void setOnClickImages(int i, int j, Group root){
        controller.getGame().getVillage().getMap()[j][i].getImageView().setOnMouseClicked(event -> {
            SideBarUI.makeBuildingsMenu(root, controller.getGame().getVillage().getMap()[j][i]);
            controller.getGame().getVillage().getMap()[j][i].getImageView().requestFocus();
        });
    }

    private static void addGlowToBuildings(ImageView imageView) {
        DropShadow ds = new DropShadow( 20, Color.AQUA );
        imageView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue ) ->
        {
            if ( newValue )
            {
                imageView.setEffect( ds );
            }
            else
            {
                imageView.setEffect( null );
            }
        });
    }

    public static Image getImageOfBuildings(String name, String type){
        Image buildingImage;
        if(type.equals(".png")) {
            File file = new File("./src/com/company/ImagesAndGifs/Buildings/" + name + type);
             buildingImage= new Image(file.toURI().toString());
        }else {
             buildingImage= gifsOfTowers.get(name);
        }
        return  buildingImage;
    }
    public static double mapCoordinates2PixelX(int x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return Screen.getPrimary().getVisualBounds().getWidth() - (x + 3) * cellWidth;
    }

    public static double mapCoordinates2PixelY(int y) {
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
