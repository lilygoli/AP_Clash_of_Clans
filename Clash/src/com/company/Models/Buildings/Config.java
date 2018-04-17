package com.company.Models.Buildings;

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
    }

    public static HashMap<String, Integer> getDictionary() {
        return dictionary;
    }
}
