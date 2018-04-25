package com.company.Exception;

import com.company.View;

public class NotEnoughFreeBuildersException extends Exception {
    public void showMessage(String message){
        View.show(message);
    }
}
