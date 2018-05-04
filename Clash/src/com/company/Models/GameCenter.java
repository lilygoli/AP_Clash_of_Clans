package com.company.Models;

import com.company.Exception.NotValidFilePathException;
import com.gilecode.yagson.YaGson;


import java.io.*;
import java.util.ArrayList;

public class GameCenter {
    private static ArrayList<Game> games=new ArrayList<Game>();//tooye khode game nabayad bashe village hayi k tala beshoon hamle karde?ys inke gameCenter ham harbar save va load she

    public static ArrayList<Game> getGames() {
        return games;
    }

    public Game loadGame(String pathName) throws NotValidFilePathException {
        String[] gameJsonAndName = loadFromFile(pathName);
        YaGson yaGson = new YaGson();
        Game mainGame = yaGson.fromJson(gameJsonAndName[0].substring(0 , gameJsonAndName[0].length() - 1), Game.class);
        games.add(mainGame);
        return mainGame;

    }

    public Game makeNewGame() {
        return new Game();
    }

    public void saveGame(Game mainGame, String pathName, String name) throws NotValidFilePathException {
        YaGson yaGson = new YaGson();
        String stringJsonOfMainGame = yaGson.toJson(mainGame);
        mainGame.setPlayerName(name);
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(pathName + "\\" + name + ".txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringJsonOfMainGame);
        } catch (IOException e) {
            throw new NotValidFilePathException();

        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (fileWriter != null)
                    fileWriter.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Game loadEnemyMap(String enemyMapPath) throws NotValidFilePathException {
        Game enemyGame = null;
        String[] EnemyJsonAndName = loadFromFile(enemyMapPath);
        for (Game game : games) {
            if(game.getPlayerName().equals(EnemyJsonAndName[1].trim())){
                return game;
            }
        }
        YaGson yaGson = new YaGson();
        EnemyMapJson enemyMapJson = yaGson.fromJson(EnemyJsonAndName[0].substring(0 , EnemyJsonAndName[0].length() - 1), EnemyMapJson.class);
        enemyGame = enemyMapJson.ConvertEnemyJsonToGame();
        enemyGame.setPlayerName(EnemyJsonAndName[1]);
        games.add(enemyGame);

        return enemyGame;//TODO tooye Controller attackedVillage e game i k tooshim set beshe ba in be allAttackedVillages ham add beshe

    }

    private String[] loadFromFile(String pathName) throws NotValidFilePathException {
        StringBuilder jsonConvertedToString = new StringBuilder();
        String[] fileNameAndContent = new String[2];
        try {
            File file = new File(pathName);
            InputStream stream = new FileInputStream(file);
            int byteCode = stream.read();
            jsonConvertedToString.append((char) byteCode);
            while (byteCode != -1) {
                byteCode = stream.read();
                jsonConvertedToString.append((char) byteCode);
            }
            fileNameAndContent[1] = file.getName().replaceAll("json|txt","").replace(".","");
            stream.close();
        } catch (IOException e) {
            throw new NotValidFilePathException();
        }
        fileNameAndContent[0] = jsonConvertedToString.toString();
        return fileNameAndContent;
    }

    public static void passTurn(int numOfTurns) {

    }
}
