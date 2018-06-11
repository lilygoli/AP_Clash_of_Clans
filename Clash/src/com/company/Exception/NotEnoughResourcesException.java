package com.company.Exception;

import com.company.UIs.UIConstants;
import com.company.View.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.File;

public class NotEnoughResourcesException extends Exception {
    ImageView imageView;

    {
        File file = new File("./src/com/company/UIs/SideBarMenuImages/NotEnoughResourcesException.png");
        Image backGround = new Image(file.toURI().toString());
        imageView = new ImageView(backGround);
        imageView.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.EXCEPTION_BUTTON_Y_COEFFICIENT* Screen.getPrimary().getVisualBounds().getHeight() );
        imageView.relocate(UIConstants.BUTTON_STARTING_X,  Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.EXCEPTION_BUTTON_Y_COEFFICIENT);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
