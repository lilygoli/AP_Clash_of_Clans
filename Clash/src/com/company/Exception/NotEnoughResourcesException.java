package com.company.Exception;

import com.company.View;

public class NotEnoughResourcesException extends Exception {
    public void showMessage(String message){

        View.show("You don't have enough resources");
    }
}
