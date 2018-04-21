package com.company.Models;

import java.util.HashMap;

public class Config {
    private static HashMap<String,Integer> dictionary=new HashMap<String,Integer>();
    {
        dictionary.put("GoldMine_GOLD_COST",150);
        dictionary.put("GoldMine_ELIXIR_COST",5);
        dictionary.put("GoldMine_JSON_TYPE",1);
        dictionary.put("GoldMine_STRENGTH",200);
        dictionary.put("GoldMine_BUILD_DURATION",0);
        dictionary.put("GoldMine_POINTS_GAINED_WHEN_DESTRUCTED",2);
        dictionary.put("GoldMine_GOLD_GAINED_WHEN_DESTRUCTED",150);
        dictionary.put("GoldMine_ELIXIR_GAINED_WHEN_DESTRUCTED",5);
        dictionary.put("GoldMine_UPGRADE_COST",100);

        dictionary.put("ElixirMine_GOLD_COST",100);
        dictionary.put("ElixirMine_ELIXIR_COST",3);
        dictionary.put("ElixirMine_JSON_TYPE",2);
        dictionary.put("ElixirMine_STRENGTH",200);
        dictionary.put("ElixirMine_BUILD_DURATION",0);
        dictionary.put("ElixirMine_POINTS_GAINED_WHEN_DESTRUCTED",2);
        dictionary.put("ElixirMine_GOLD_GAINED_WHEN_DESTRUCTED",100);
        dictionary.put("ElixirMine_ELIXIR_GAINED_WHEN_DESTRUCTED",3);
        dictionary.put("ElixirMine_UPGRADE_COST",100);

        dictionary.put("GoldStorage_GOLD_COST",200);
        dictionary.put("GoldStorage_ELIXIR_COST",0);
        dictionary.put("GoldStorage_JSON_TYPE",3);
        dictionary.put("GoldStorage_STRENGTH",300);
        dictionary.put("GoldStorage_BUILD_DURATION",0);
        dictionary.put("GoldStorage_POINTS_GAINED_WHEN_DESTRUCTED",3);
        dictionary.put("GoldStorage_GOLD_GAINED_WHEN_DESTRUCTED",200);
        dictionary.put("GoldStorage_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("GoldStorage_UPGRADE_COST",100);

        dictionary.put("ElixirStorage_GOLD_COST",200);
        dictionary.put("ElixirStorage_ELIXIR_COST",0);
        dictionary.put("ElixirStorage_JSON_TYPE",4);
        dictionary.put("ElixirStorage_STRENGTH",300);
        dictionary.put("ElixirStorage_BUILD_DURATION",0);
        dictionary.put("ElixirStorage_POINTS_GAINED_WHEN_DESTRUCTED",3);
        dictionary.put("ElixirStorage_GOLD_GAINED_WHEN_DESTRUCTED",200);
        dictionary.put("ElixirStorage_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("ElixirStorage_UPGRADE_COST",100);

        dictionary.put("MainBuilding_GOLD_COST",200);
        dictionary.put("MainBuilding_ELIXIR_COST",0);
        dictionary.put("MainBuilding_JSON_TYPE",5);
        dictionary.put("MainBuilding_STRENGTH",1000);
        dictionary.put("MainBuilding_BUILD_DURATION",100);
        dictionary.put("MainBuilding_POINTS_GAINED_WHEN_DESTRUCTED",8);
        dictionary.put("MainBuilding_GOLD_GAINED_WHEN_DESTRUCTED",200);
        dictionary.put("MainBuilding_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("MainBuilding_UPGRADE_POINTS_NEEDED",5);

        dictionary.put("Camp_GOLD_COST",200);
        dictionary.put("Camp_ELIXIR_COST",0);
        dictionary.put("Camp_JSON_TYPE",7);
        dictionary.put("Camp_STRENGTH",900);
        dictionary.put("Camp_BUILD_DURATION",100);
        dictionary.put("Camp_POINTS_GAINED_WHEN_DESTRUCTED",1);
        dictionary.put("Camp_GOLD_GAINED_WHEN_DESTRUCTED",200);
        dictionary.put("Camp_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("Camp_UPGRADE_POINTS_NEEDED",0);
        dictionary.put("Camp_CAPACITY",50);

        dictionary.put("Barrack_GOLD_COST",200);
        dictionary.put("Barrack_ELIXIR_COST",0);
        dictionary.put("Barrack_JSON_TYPE",6);
        dictionary.put("Barrack_STRENGTH",300);
        dictionary.put("Barrack_BUILD_DURATION",100);
        dictionary.put("Barrack_POINTS_GAINED_WHEN_DESTRUCTED",1);
        dictionary.put("Barrack_GOLD_GAINED_WHEN_DESTRUCTED",200);
        dictionary.put("Barrack_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("Barrack_UPGRADE_POINTS_NEEDED",0);

        dictionary.put("Grass_GOLD_COST",0);
        dictionary.put("Grass_ELIXIR_COST",0);
        dictionary.put("Grass_JSON_TYPE",0);
        dictionary.put("Grass_STRENGTH",0);
        dictionary.put("Grass_BUILD_DURATION",0);
        dictionary.put("Grass_POINTS_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("Grass_GOLD_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("Grass_ELIXIR_GAINED_WHEN_DESTRUCTED",0);
        dictionary.put("Grass_UPGRADE_POINTS_NEEDED",0);


        dictionary.put("Guardian_ELEXIR_COST",150);
        dictionary.put("Guardian_BUILD_DURATION",10);
        dictionary.put("Guardian_HEALTH",100);
        dictionary.put("Guardian_RADIUS",1);
        dictionary.put("Guardian_DAMAGE",10);
        dictionary.put("Guardian_MAXSPEED",2);
        dictionary.put("Guardian_ADDED_HEALTH",5);
        dictionary.put("Guardian_ADDED_DAMAGE",1);
        dictionary.put("Guardian_UNLOCKLEVEL",0);
        dictionary.put("Guardian_CAN_FLY",0);

        dictionary.put("Giant_ELEXIR_COST",150);
        dictionary.put("Giant_BUILD_DURATION",30);
        dictionary.put("Giant_HEALTH",500);
        dictionary.put("Giant_RADIUS",1);
        dictionary.put("Giant_DAMAGE",30);
        dictionary.put("Giant_MAXSPEED",1);
        dictionary.put("Giant_ADDED_HEALTH",5);
        dictionary.put("Giant_ADDED_DAMAGE",1);
        dictionary.put("Giant_UNLOCKLEVEL",0);
        dictionary.put("Giant_CAN_FLY",0);

        dictionary.put("Dragon_ELEXIR_COST",200);
        dictionary.put("Dragon_BUILD_DURATION",45);
        dictionary.put("Dragon_HEALTH",700);
        dictionary.put("Dragon_RADIUS",3);
        dictionary.put("Dragon_DAMAGE",30);
        dictionary.put("Dragon_MAXSPEED",6);
        dictionary.put("Dragon_ADDED_HEALTH",5);
        dictionary.put("Dragon_ADDED_DAMAGE",1);
        dictionary.put("Dragon_UNLOCKLEVEL",0);
        dictionary.put("Dragon_CAN_FLY",1);

        dictionary.put("Archer_ELEXIR_COST",75);
        dictionary.put("Archer_BUILD_DURATION",10);
        dictionary.put("Archer_HEALTH",100);
        dictionary.put("Archer_RADIUS",10);
        dictionary.put("Archer_DAMAGE",10);
        dictionary.put("Archer_MAXSPEED",2);
        dictionary.put("Archer_ADDED_HEALTH",5);
        dictionary.put("Archer_ADDED_DAMAGE",1);
        dictionary.put("Archer_UNLOCKLEVEL",0);
        dictionary.put("Archer_CAN_FLY",0);

        dictionary.put("WallBreaker_ELEXIR_COST",40);
        dictionary.put("WallBreaker_BUILD_DURATION",10);
        dictionary.put("WallBreaker_HEALTH",100);
        dictionary.put("WallBreaker_RADIUS",1);
        dictionary.put("WallBreaker_DAMAGE",50);
        dictionary.put("WallBreaker_MAXSPEED",6);
        // TODO: 4/18/2018 add when we have no wall
        dictionary.put("WallBreaker_ADDED_HEALTH",5);
        dictionary.put("WallBreaker_ADDED_DAMAGE",1);
        dictionary.put("WallBreaker_UNLOCKLEVEL",0);
        dictionary.put("WallBreaker_CAN_FLY",0);

        dictionary.put("Healer_ELEXIR_COST",175);
        dictionary.put("Healer_BUILD_DURATION",30);
        dictionary.put("Healer_HEALTH",200);
        dictionary.put("Healer_RADIUS",10);
        dictionary.put("Healer_DAMAGE",0);
        dictionary.put("Healer_MAXSPEED",3);
        dictionary.put("Healer_UNLOCKLEVEL",0);
        dictionary.put("Healer_HEAL",25);
        dictionary.put("Healer_ADDED_HEAL",1);
        dictionary.put("Healer_CAN_FLY",1);
        dictionary.put("Healer_ALIVE_TIME",10);

    }

    public static HashMap<String, Integer> getDictionary() {
        return dictionary;
    }
}