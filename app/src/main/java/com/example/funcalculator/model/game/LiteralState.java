package com.example.funcalculator.model.game;

import java.util.Collections;
import java.util.Map;

public class LiteralState {
    private Map<Character, Integer> _stateMap;

    public LiteralState(){
        _stateMap = initMap();
    }

    public Map<Character, Integer> initMap(){
        Map<Character, Integer> map = Collections.emptyMap();

        for (int i = 0; i < 10; i++){
            map.put((char) i, 0);
        }
        
        map.put('+', 0);
        map.put('-', 0);
        map.put('*', 0);
        map.put('/', 0);
        map.put('=', 0);
        map.put('.', 0);
        return map;
    }

    public void modifyStateOf(Character literal, int state){
        _stateMap.put(literal, state);
    }

}
