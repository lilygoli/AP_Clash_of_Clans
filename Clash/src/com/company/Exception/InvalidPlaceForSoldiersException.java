package com.company.Exception;

import com.company.UIs.UIConstants;
import com.company.View.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.File;

public class InvalidPlaceForSoldiersException extends Exception{
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void showMessage(){
        File file = new File("./src/com/company/UIs/SideBarMenuImages/InvalidPlaceForSoldiersException.png");
        Image backGround = new Image(file.toURI().toString());
        imageView = new ImageView(backGround);
        imageView.relocate(UIConstants.BUTTON_STARTING_X,  Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.EXCEPTION_BUTTON_Y_COEFFICIENT);
    }
}
