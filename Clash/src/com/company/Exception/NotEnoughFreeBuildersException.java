package com.company.Exception;

import com.company.UIs.UIConstants;
import com.company.View.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.File;

public class NotEnoughFreeBuildersException extends Exception {
    ImageView imageView ;
    {
        File file = new File("./src/com/company/UIs/SideBarMenuImages/NotEnoughFreeBuildersException.png");
        Image backGround = new Image(file.toURI().toString());
        imageView = new ImageView(backGround);
        imageView.relocate(UIConstants.BUTTON_STARTING_X, Screen.getPrimary().getVisualBounds().getHeight() *0.4);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
