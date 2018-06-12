package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Towers.Defences.AirDefence;
import com.company.Models.Village;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;

public class MapUI extends Application {
    private static int buildX;
    private static int buildY;
    private PannableCanvas canvas = new PannableCanvas();
    private static HashMap<String ,Image> gifsOfTowers=new HashMap<>();
    private static boolean isInBuildMenu=false;

    public static void setIsInBuildMenu(boolean isInBuildMenu) {
        MapUI.isInBuildMenu=isInBuildMenu;
    }

    private Controller controller = new Controller();
// to be moved to a new thread
// TODO: 6/11/2018 giant kill gif add and healer and wallbreaker and guardian giant kill add
    static {
        File AirDefenceFile=new File("./src/com/company/ImagesAndGifs/Buildings/AirDefenceLoading.gif");
        gifsOfTowers.put("AirDefence",new Image(AirDefenceFile.toURI().toString()));
        File ArcherTowerFile=new File("./src/com/company/ImagesAndGifs/Buildings/ArcherTowerLoading.gif");
        gifsOfTowers.put("ArcherTower",new Image(ArcherTowerFile.toURI().toString()));
        File CampFile=new File("./src/com/company/ImagesAndGifs/Buildings/CampLoading.gif");
        gifsOfTowers.put("Camp",new Image(CampFile.toURI().toString()));
        File CannonFile=new File("./src/com/company/ImagesAndGifs/Buildings/CannonLoading.gif");
        gifsOfTowers.put("Cannon",new Image(CannonFile.toURI().toString()));
        File ElixirMineFile=new File("./src/com/company/ImagesAndGifs/Buildings/ElixirMineLoading.gif");
        gifsOfTowers.put("ElixirMine",new Image(ElixirMineFile.toURI().toString()));
        File ElixirStorageFile=new File("./src/com/company/ImagesAndGifs/Buildings/ElixirStorageLoading.gif");
        gifsOfTowers.put("ElixirStorage",new Image(ElixirStorageFile.toURI().toString()));
        File GoldMineFile=new File("./src/com/company/ImagesAndGifs/Buildings/GoldMineLoading.gif");
        gifsOfTowers.put("GoldMine",new Image(GoldMineFile.toURI().toString()));
        File GoldStorageFile=new File("./src/com/company/ImagesAndGifs/Buildings/GoldStorageLoading.gif");
        gifsOfTowers.put("GoldStorage",new Image(GoldStorageFile.toURI().toString()));
        File WallFile=new File("./src/com/company/ImagesAndGifs/Buildings/WallLoading.gif");
        gifsOfTowers.put("Wall",new Image(WallFile.toURI().toString()));
        File WizardTowerFile=new File("./src/com/company/ImagesAndGifs/Buildings/WizardTowerLoading.gif");
        gifsOfTowers.put("WizardTower",new Image(WizardTowerFile.toURI().toString()));
        File BarrackFile=new File("./src/com/company/ImagesAndGifs/Buildings/BarrackLoading.gif");
        gifsOfTowers.put("Barrack",new Image(BarrackFile.toURI().toString()));
}
    public static int getBuildX() {
        return buildX;
    }

    public PannableCanvas getCanvas() {
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

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        SideBarUI.makeSideBar(root);

        makeSlider(root);
    }

    private void makeSlider(Group root) {
        int maxDeltaT = UIConstants.DELTA_T;
        final Slider deltaTSlider = new Slider(0, maxDeltaT, maxDeltaT);
        final Label caption = new Label("Delta T");
        final Label deltaTValue = new Label(Double.toString(deltaTSlider.getValue()));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(70);


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

        grid.relocate(Screen.getPrimary().getVisualBounds().getWidth() * 0.72, Screen.getPrimary().getVisualBounds().getHeight() * 0.88);

        root.getChildren().add(grid);
        SideBarUI.makeStartingMenu(root);
    }


    private void makeGameBoard(Group root , Scene scene) throws FileNotFoundException {
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

                if (!(i <= 1 || i >= 32 || j == 0 || j == 31)) {
                    imageView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    {

                        if (newValue) {
                            if (isInBuildMenu) {
                                try {
                                    FileInputStream fileInputStream2 = new FileInputStream("./src/com/company/ImagesAndGifs/red.png");
                                    imageView.setImage(new Image(fileInputStream2));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
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
                    buildX = finalI - 2;
                    buildY = finalJ - 1;
                    imageView.requestFocus();
                });
            }

        }
    }
    public void showMapInVillage(Village village, Group root) {

        AnimationTimer animationTimer=new AnimationTimer(){

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
                                    }
                                    putBuildingImageInMap(i, j, village,16);
                                }

                            } else {
                                if(village.getMap()[j][i].getUnderConstructionStatus()){
                                    village.getMap()[j][i].setImage( getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".gif"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        setOnClickImages(i, j, root);
                                    }
                                    putBuildingImageInMap(i, j, village,32);
                                }else {
                                    village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
                                    if(!village.getMap()[j][i].getEventSet()){
                                        setOnClickImages(i, j, root);
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
        animationTimer.start();
    }

    private void putBuildingImageInMap(int i, int j, Village village,int size) {
        village.getMap()[j][i].getImage().setX(mapCoordinates2PixelX(j));
        village.getMap()[j][i].getImage().setY(mapCoordinates2PixelY(i));
        addGlowToBuildings(village.getMap()[j][i].getImage());
        village.getMap()[j][i].getImage().setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / size);
        village.getMap()[j][i].getImage().setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / size);
//            if (canvas.getChildren().contains(village.getMap()[j][i].getImage())) {
//                canvas.getChildren().remove(village.getMap()[j][i].getImage());
//            }
//            canvas.getChildren().add(village.getMap()[j][i].getImage());
       //todo what is wrong
    }


    private void setOnClickImages(int i,int j,Group root){
        controller.getGame().getVillage().getMap()[j][i].getImage().setOnMouseClicked(event -> {
            SideBarUI.makeBuildingsMenu(root, controller.getGame().getVillage().getMap()[j][i]);
            controller.getGame().getVillage().getMap()[j][i].getImage().requestFocus();
        });
    }

    private void addGlowToBuildings(ImageView imageView) {
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

    private static Image getImageOfBuildings(String name,String type){
        Image buildingImage;
        if(type.equals(".png")) {
            File file = new File("./src/com/company/ImagesAndGifs/Buildings/" + name + type);
             buildingImage= new Image(file.toURI().toString());
        }else {
             buildingImage= gifsOfTowers.get(name);
        }
        return  buildingImage;
    }
    private static double mapCoordinates2PixelX(int x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return Screen.getPrimary().getVisualBounds().getWidth() - (x + 3) * cellWidth;
    }

    private static double mapCoordinates2PixelY(int y) {
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
