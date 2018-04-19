package com.company.Models;

public class Builder {
    private int number;
    private boolean isOccupied;
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
