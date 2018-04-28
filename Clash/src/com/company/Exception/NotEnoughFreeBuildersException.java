package com.company.Exception;

import com.company.View;

public class NotEnoughFreeBuildersException extends Exception {
    public void showMessage(){
        View.show("You don't have enough builders");
    }
}
