package com.company.Exception;

public class NotEnoughResourcesException extends Exception {
    public String getExceptionMassage(){
        return "You don't have enough resources";
    }
}
