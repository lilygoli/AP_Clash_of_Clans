package com.company.Models;

import com.company.Exception.NotValidFilePathException;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class GameCenter {
    private static ArrayList<Game> games;//tooye khode game nabayad bashe village hayi k tala beshoon hamle karde?ys inke gameCenter ham harbar save va load she

    public static ArrayList<Game> getGames() {
        return games;
    }

    public Game loadGame(String pathName) throws NotValidFilePathException {
        String[] gameJsonAndName = loadFromFile(pathName);
        Gson gson = new Gson();
        Game mainGame = gson.fromJson(gameJsonAndName[0], Game.class);
        games.add(mainGame);
        return mainGame;

    }

    public Game makeNewGame() {
        //TODO
        return null;
    }

    public void saveGame(Game mainGame, String pathName,String name) {
        Gson gson = new Gson();
        String stringJsonOfMainGame = gson.toJson(mainGame);
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(pathName+"\\"+name+".txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringJsonOfMainGame);
        } catch (IOException e) {
            e.printStackTrace();

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
        Gson gson = new Gson();
        EnemyMapJson enemyMapJson = gson.fromJson(EnemyJsonAndName[0], EnemyMapJson.class);
        enemyGame = enemyMapJson.ConvertEnemyJsonToGame();
        enemyGame.setPlayerName(EnemyJsonAndName[1]);
        games.add(enemyGame);

        return enemyGame;//TODO tooye controller attackedVillage e game i k tooshim set beshe ba in

    }

    private String[] loadFromFile(String pathName) throws NotValidFilePathException {
        StringBuilder jsonConvertedToString = new StringBuilder();
        String[] fileNameAndContent = new String[2];
        try {
            File file = new File(pathName);
            InputStream stream = new FileInputStream(file);
            int byteCode = stream.read();
            while (byteCode != -1) {
                byteCode = stream.read();
                jsonConvertedToString.append((char) byteCode);
            }
            fileNameAndContent[1] = file.getName();
            stream.close();
        } catch (IOException e) {
            throw new NotValidFilePathException();
        }
        fileNameAndContent[0] = jsonConvertedToString.toString();
        return fileNameAndContent;

    }
}
