package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Towers.Buildings.ElixirStorage;
import com.company.Models.Towers.Buildings.GoldStorage;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapUI  {
    private static int buildX;
    private static int buildY;
    private static PannableCanvas canvas = new PannableCanvas();
    private static HashMap<String ,Image> gifsOfTowers=new HashMap<>();
    private static boolean isInBuildMenu=false;
    private static AnimationTimer showMapAnimationTimer;
    private static boolean verticalOrientationOfWall=false;
    private static boolean isInDefense = false;
    static final BooleanProperty cPressed = new SimpleBooleanProperty(false);
    static final BooleanProperty hPressed = new SimpleBooleanProperty(false);
    static final BooleanProperty ePressed = new SimpleBooleanProperty(false);
    static final BooleanProperty aPressed = new SimpleBooleanProperty(false);
    static final BooleanProperty tPressed = new SimpleBooleanProperty(false);
    static final BooleanBinding cheat = cPressed.and(hPressed.and(ePressed.and(aPressed.and(tPressed))));

    public static void setIsInBuildMenu(boolean isInBuildMenu) {
        MapUI.isInBuildMenu=isInBuildMenu;
    }

    public static HashMap<String, Image> getGifsOfTowers() {
        return gifsOfTowers;
    }

    private static Controller controller = new Controller();

    public static void isIsInDefense( boolean isInDefense1) {
        isInDefense=isInDefense1;
    }

    public static boolean isIsInDefense() {
        return isInDefense;
    }

    public static boolean getVerticalOrientationOfWall() {
        return verticalOrientationOfWall;
    }

    public static void setVerticalOrientationOfWall(boolean verticalOrientationOfWall) {
        MapUI.verticalOrientationOfWall = verticalOrientationOfWall;
    }
    // to be moved to a new thread
// TODO: 6/11/2018 giant kill gif add and healer and wallbreaker and guardian giant kill add

    static {
        File MainBuildingFile = new File("./src/com/company/ImagesAndGifs/Buildings/MainBuildingLoading.gif");
        gifsOfTowers.put("MainBuildingLoading", new Image(MainBuildingFile.toURI().toString()));
        File AirDefenceFile = new File("./src/com/company/ImagesAndGifs/Buildings/AirDefenceLoading.gif");
        gifsOfTowers.put("AirDefenceLoading", new Image(AirDefenceFile.toURI().toString()));
        File GuardianGiantGif = new File("./src/com/company/ImagesAndGifs/Buildings/GuardianGiant.gif");
        gifsOfTowers.put("GuardianGiant", new Image(GuardianGiantGif.toURI().toString()));
        File GuardianGiantLoadingGif = new File("./src/com/company/ImagesAndGifs/Buildings/GuardianGiantLoading.gif");
        gifsOfTowers.put("GuardianGiantLoading", new Image(GuardianGiantLoadingGif.toURI().toString()));
        AirDefenceFile = new File("./src/com/company/ImagesAndGifs/Buildings/AirDefence.gif");
        gifsOfTowers.put("AirDefence", new Image(AirDefenceFile.toURI().toString()));
        File ArcherTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/ArcherTowerLoading.gif");
        gifsOfTowers.put("ArcherTowerLoading", new Image(ArcherTowerFile.toURI().toString()));
        ArcherTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/ArcherTower.gif");
        gifsOfTowers.put("ArcherTower", new Image(ArcherTowerFile.toURI().toString()));
        File CampFile = new File("./src/com/company/ImagesAndGifs/Buildings/CampLoading.gif");
        gifsOfTowers.put("CampLoading", new Image(CampFile.toURI().toString()));
        File CannonFile = new File("./src/com/company/ImagesAndGifs/Buildings/CannonLoading.gif");
        gifsOfTowers.put("CannonLoading", new Image(CannonFile.toURI().toString()));
        CannonFile = new File("./src/com/company/ImagesAndGifs/Buildings/Cannon.gif");
        gifsOfTowers.put("Cannon", new Image(CannonFile.toURI().toString()));
        File ElixirMineFile = new File("./src/com/company/ImagesAndGifs/Buildings/ElixirMineLoading.gif");
        gifsOfTowers.put("ElixirMineLoading", new Image(ElixirMineFile.toURI().toString()));
        File ElixirStorageFile = new File("./src/com/company/ImagesAndGifs/Buildings/ElixirStorageLoading.gif");
        gifsOfTowers.put("ElixirStorageLoading", new Image(ElixirStorageFile.toURI().toString()));
        File GoldMineFile = new File("./src/com/company/ImagesAndGifs/Buildings/GoldMineLoading.gif");
        gifsOfTowers.put("GoldMineLoading", new Image(GoldMineFile.toURI().toString()));
        File GoldStorageFile = new File("./src/com/company/ImagesAndGifs/Buildings/GoldStorageLoading.gif");
        gifsOfTowers.put("GoldStorageLoading", new Image(GoldStorageFile.toURI().toString()));
        File WallFile = new File("./src/com/company/ImagesAndGifs/Buildings/WallLoading.gif");
        gifsOfTowers.put("WallLoading", new Image(WallFile.toURI().toString()));
        File WizardTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/WizardTowerLoading.gif");
        gifsOfTowers.put("WizardTowerLoading", new Image(WizardTowerFile.toURI().toString()));
        WizardTowerFile = new File("./src/com/company/ImagesAndGifs/Buildings/WizardTower.gif");
        gifsOfTowers.put("WizardTower", new Image(WizardTowerFile.toURI().toString()));
        File BarrackFile = new File("./src/com/company/ImagesAndGifs/Buildings/BarrackLoading.gif");
        gifsOfTowers.put("BarrackLoading", new Image(BarrackFile.toURI().toString()));
        File TrapFile = new File("./src/com/company/ImagesAndGifs/Buildings/TrapLoading.gif");
        gifsOfTowers.put("TrapLoading", new Image(TrapFile.toURI().toString()));
        File TrapGifFile = new File("./src/com/company/ImagesAndGifs/Buildings/Trap.gif");
        gifsOfTowers.put("Trap", new Image(TrapGifFile.toURI().toString()));
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


    public static Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public static void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        Media sound = new Media(new File("./src/com/company/ImagesAndGifs/Maze.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        final boolean[] isSongPuased = {false};
        // TODO: 7/11/2018 repeat sound
        File file = new File("./src/com/company/UIs/MapResources/MapBackGround.jpg");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        backGroundView.setOpacity(0.7);
        root.getChildren().add(backGroundView);

        scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.C){
                    System.out.println("c");
                    cPressed.set(true);
                    hPressed.set(false);
                    ePressed.set(false);
                    aPressed.set(false);
                    tPressed.set(false);
                }
                else if (event.getCode() == KeyCode.H){
                    System.out.println("h");
                    hPressed.set(true);
                    ePressed.set(false);
                    aPressed.set(false);
                    tPressed.set(false);
                }
                else if (event.getCode() == KeyCode.E){
                    System.out.println("e");
                    ePressed.set(true);
                    aPressed.set(false);
                    tPressed.set(false);
                }
                else if (event.getCode() == KeyCode.A){
                    System.out.println("a");
                    aPressed.set(true);
                    tPressed.set(false);
                }
                else if (event.getCode() == KeyCode.T){
                    System.out.println("t");
                    tPressed.set(true);
                }
                else if (event.getCode() == KeyCode.SPACE){
                    if (isSongPuased[0]) {
                        mediaPlayer.play();
                        isSongPuased[0] = false;
                    }
                    else if (!isSongPuased[0]){
                        mediaPlayer.pause();
                        isSongPuased[0] = true;
                    }
                    cPressed.set(false);
                    hPressed.set(false);
                    ePressed.set(false);
                    aPressed.set(false);
                    tPressed.set(false);
                }
                else{
                    cPressed.set(false);
                    hPressed.set(false);
                    ePressed.set(false);
                    aPressed.set(false);
                    tPressed.set(false);
                }
            }
        });
        cheat.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                for (ElixirStorage elixirStorage : controller.getGame().getVillage().getElixirStorages()) {
                    elixirStorage.addToStorage(elixirStorage.getCapacity());
                }
                for (GoldStorage goldStorage : controller.getGame().getVillage().getGoldStorages()) {
                    goldStorage.addToStorage(goldStorage.getCapacity());
                }
                cPressed.set(false);
                hPressed.set(false);
                ePressed.set(false);
                aPressed.set(false);
                tPressed.set(false);
            }
        });
        controller.getGame().getVillage().getGainedResource().setGold(0);
        controller.getGame().getVillage().getGainedResource().setElixir(0);
        try {
            if (!AttackMapUI.getWinningLabel().getText().equals("")) {
                new Timeline(new KeyFrame(Duration.seconds(6), new KeyValue(AttackMapUI.getWinningLabel().textProperty(), null))).play();
                root.getChildren().add(AttackMapUI.getWinningLabel());
                AttackMapUI.getWinningLabel().setText("");
            }
        }catch (NullPointerException ignored){

        }
        makeGameBoard(root ,scene);

//        file = new File("./src/com/company/UIs/MapResources/mapBorder.png");
//        Image mapBorder = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
//        ImageView mapBorderView = new ImageView(mapBorder);
//        mapBorderView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 1.3);
//        mapBorderView.relocate(Screen.getPrimary().getVisualBounds().getWidth() - Screen.getPrimary().getVisualBounds().getHeight() * 1.09, -Screen.getPrimary().getVisualBounds().getHeight() / 5.9);
//        canvas.getChildren().add(mapBorderView);
//        mapBorderView.setScaleY(0.9);
//        mapBorderView.setScaleX(0.95);
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
        final Slider deltaTSlider = new Slider(0.01, maxDeltaT, maxDeltaT);
        final Label caption = new Label("Time Unit");
        caption.setFont(Font.font("Papyrus",FontWeight.BOLD,13));
        caption.setTextFill(Color.DARKRED);
        final Label deltaTValue = new Label(Double.toString(deltaTSlider.getValue()));
        deltaTValue.setTextFill(Color.DARKRED);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

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

        grid.relocate(Screen.getPrimary().getVisualBounds().getWidth() * 0.21, Screen.getPrimary().getVisualBounds().getHeight() * 0.88);

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
            private int flag = 0;
            private ArrayList<Soldier> previousTroops= new ArrayList<>();

            @Override
            public void handle(long now) {
                if (isInDefense) {
                    try {
                        while (MapUI.getController().getGame().getTroops()== null) {

                        }
                        if(flag==0) {
                            AttackMapUI.makeAttackGameBoard(SideBarUI.primaryStage, MapUI.getController());
                            flag=1;
                        }
                        //  Thread.sleep(100);
//                        for (Soldier previousTroop : previousTroops) {
//                            previousTroop.getImageView().setImage(null);
//                            canvas.getChildren().remove(previousTroop.getLeftHealth());
//                            canvas.getChildren().remove(previousTroop.getAllHealth());
//                        }
//                        canvas.getChildren().removeIf(x-> x.getClass().getSimpleName().equals("Rectangle"));
//                        previousTroops.clear();
                        ArrayList<Soldier> soldiers = MapUI.getController().getGame().getTroops();
                        for (Soldier soldier : soldiers) {
                            if(!soldier.isHasPut()) {
                                AttackMapUI.putSoldiersImageInMap((int) Math.ceil(soldier.getY()), (int) Math.ceil(soldier.getX()), 32, AttackMapUI.canvas, AttackMapUI.getSoldiersGif().get(soldier.getClass().getSimpleName() + "MoveUp"), soldier, root);
                                soldier.setHasPut(true);
                            }
//                                System.out.println("putting image");
//                                soldier.setHasPut(true);
//                                AttackMapUI.putSoldiersImageInMap((int) Math.ceil(soldier.getY()), (int) Math.ceil(soldier.getX()), 32, AttackMapUI.canvas, AttackMapUI.getSoldiersGif().get(soldier.getClass().getSimpleName() + "MoveUp"), soldier, root);
//                                previousTroops.add(soldier);
//                            }
                        }
                        if(controller.getGame().isWarFinished()){
                            isInDefense = false;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (now - lastUpdate >= 1000000000) {
                        int flag = 0;
                        for (int i = 0; i < 30; i++) {
                            for (int j = 0; j < 30; j++) {
                                if (village.getMap()[j][i].getClass() == Grass.class) {
                                    continue;
                                } else if (village.getMap()[j][i].getClass() == MainBuilding.class) {
                                    if (flag == 0) {
                                        flag = 1;
                                        if (village.getMap()[j][i].getTimeLeftOfUpgrade() > 0) {
                                            village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(), ".gif", false));
                                        } else {
                                            village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(), ".png", false));
                                        }
                                        if (!village.getMap()[j][i].getEventSet()) {
                                            setOnClickImages(14, 14, root);
                                            village.getMap()[j][i].setIsEventSet(true);
                                        }
                                        putBuildingImageInMap(i, j, village, 16, canvas, 0);
                                    }

                                } else {
                                    if (village.getMap()[j][i].getUnderConstructionStatus() || village.getMap()[j][i].getTimeLeftOfUpgrade() > 0) {
                                        village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(), ".gif", false));
                                        if (!village.getMap()[j][i].getEventSet()) {
                                            setOnClickImages(i, j, root);
                                            village.getMap()[j][i].setIsEventSet(true);
                                        }
                                        putBuildingImageInMap(i, j, village, 32, canvas, -1);
                                    } else {

                                        if (village.getMap()[j][i].getClass().getSimpleName().equals("Wall") && verticalOrientationOfWall) {
                                            village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName() + "2", ".png", false));
                                        } else {
                                            village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(), ".png", false));
                                        }
                                        if (!village.getMap()[j][i].getEventSet()) {
                                            setOnClickImages(i, j, root);
                                            village.getMap()[j][i].setIsEventSet(true);
                                        }
                                        putBuildingImageInMap(i, j, village, 32, canvas, -1);
                                    }
                                }
                            }
                        }
                        lastUpdate = now;
                    }
                }
            }
        };
        showMapAnimationTimer.start();
    }

    public static void putBuildingImageInMap(int i, int j, Village village, int size, PannableCanvas canvas,int adjust) {
        village.getMap()[j][i].getImageView().setX(mapCoordinates2PixelX(j+1+adjust));
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

    public static Image getImageOfBuildings(String name, String type , boolean inWar){
        Image buildingImage = null;
        if(type.equals(".png")) {
            if (!inWar || !name.equals("Trap")) {
                File file = new File("./src/com/company/ImagesAndGifs/Buildings/" + name + type);
                buildingImage = new Image(file.toURI().toString());
            }
        }else {
            if (!inWar)
             buildingImage= gifsOfTowers.get(name + "Loading");
            else{
                buildingImage= gifsOfTowers.get(name);
            }
        }
        return  buildingImage;
    }
    public static double mapCoordinates2PixelX(double x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return Screen.getPrimary().getVisualBounds().getWidth() - (x + 3) * cellWidth;
    }

    public static double mapCoordinates2PixelY(double y) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return (y + 1) * cellWidth;
    }

    private static int pixel2MapCoordinatesX(double x) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        x = x - Screen.getPrimary().getVisualBounds().getWidth();
        x = - x / cellWidth;
        return (int)(x - 3);
    }

    private static int pixel2MapCoordinatesY(double y) {
        double cellWidth = Screen.getPrimary().getVisualBounds().getHeight() / 32;
        return (int) (y / cellWidth - 1);
    }
}
