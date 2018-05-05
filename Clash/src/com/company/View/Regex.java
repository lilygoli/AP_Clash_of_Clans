package com.company.View;

public class Regex {
    public static final String SAVING_GAME_REGEX  = "save (.+) (.+)";
    public static final String PASSING_TURN_REGEX = "turn (\\d+)";
    public static final String SELECT_BUILDING_REGEX = "(.+)* (\\d+)";
    public static final  String PUT_UNIT_REGEX="Put (//D+) (//d+) in (//d+) (//d+)";
    public static final String STATUS_UNIT_TYPE ="status unit (\\D+)";
    public static final String STATUS_TOWER_TYPE ="status tower (\\D+)";
}
