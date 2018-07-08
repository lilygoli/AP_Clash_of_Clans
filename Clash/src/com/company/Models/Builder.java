package com.company.Models;

import java.io.Serializable;

public class Builder implements Serializable {
    private int number;
    private boolean isOccupied;
    private int x, y;
    public Builder(int number){
        this.number=number;
        this.isOccupied=false;
    }

    public int getNumber() {
        return number;
    }
    public boolean getOccupationState(){
        return isOccupied;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOccupationState(boolean occupied) {
        isOccupied = occupied;
    }
}
