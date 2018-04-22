package com.company.Exception;

public class NotValidFilePathException extends Exception {
    public String getExceptionMassage(){
     return "There is no valid file in this location.";
    }
}
