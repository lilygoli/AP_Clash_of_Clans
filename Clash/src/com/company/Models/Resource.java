package com.company.Models;

import java.io.Serializable;

public class Resource implements Serializable {
    private int gold;
    private int elixir;
    public Resource(int gold,int elixir){
        this.gold = gold;
        this.elixir = elixir;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }
}
