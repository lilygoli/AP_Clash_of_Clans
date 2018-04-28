package com.company.Exception;

import com.company.View;

public class NotValidFilePathException extends Exception {
    public void showExceptionMassage(){
        View.show("There is no valid file in this location.");
    }
}
