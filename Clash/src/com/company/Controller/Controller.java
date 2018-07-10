package com.company.Controller;

import com.company.Exception.*;
import com.company.Models.*;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Cell;
import com.company.View.Regex;
import com.company.View.View;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Game game = null;
    private GameCenter gameCenter = new GameCenter();

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameCenter getGameCenter() {
        return gameCenter;
    }
}
