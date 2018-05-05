package com.company.Exception;

import com.company.View.View;

public class NotEnoughResourcesException extends Exception {
    public void showMessage(){

        View.show("You don't have enough resources");
    }
}
