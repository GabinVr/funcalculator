package com.example.funcalculator.model.game;

import com.example.funcalculator.model.equation.EquationScanner;
import com.example.funcalculator.model.pair.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameModel {
    private static final int GAME_FINISHED = 1;
    private static final int GAME_IN_PROGRESS = 0;
    private static final int MAX_TRIES = 4;
    private static final int MAX_STACK_CAPACITY = 5;
    private static final int WON = 1;
    private static final int LOST = -1;
    private static final int IN_PROGRESS = 0;

    private static final int COLOR_VALID = 1;
    private static final int COLOR_BLANK = 0;
    private static final int COLOR_INVALID = -1;
    private static final int COLOR_MISPLACED = 2;

    private int _gameState = GAME_IN_PROGRESS;
    private int _tries = 0;
    private int _win = IN_PROGRESS;
    private final Stack<Character> _currentEquation;
    private final Stack<Character> _targetEquation;
    private List<List<Pair>> _states;

    public GameModel(String targetEquation) {
        if (targetEquation.length() != MAX_STACK_CAPACITY) {
            Log.d("GameModel", "Invalid equation");
            throw new IllegalArgumentException("Invalid equation");
        }
        _currentEquation = new Stack<>();
        _targetEquation = new Stack<>();
        _states = new ArrayList<>();
        for (int i = 0; i < MAX_TRIES; i++) {
            _states.add(new ArrayList<>());
            for (int j = 0; j < MAX_STACK_CAPACITY; j++) {
                _states.get(i).add(new Pair(COLOR_BLANK, ' '));
            }
        }
        for (int i = 0; i < targetEquation.length(); i++) {
            _targetEquation.add(targetEquation.charAt(i));
        }
    }

    /**
     * Updates the state of the game by adding the current equation to the list of states
     * It sets the color of each character to blank
     */
    public void updateState() {
        List<Pair> currentState = new ArrayList<>();
        for (int i = 0; i < _currentEquation.size(); i++) {
            char content = _currentEquation.get(i);
            currentState.add(new Pair(COLOR_BLANK, content));
        }
        for (int i = _currentEquation.size(); i < MAX_STACK_CAPACITY; i++) {
            currentState.add(new Pair(COLOR_BLANK, ' '));
        }
        _states.set(_tries, currentState);
    }

    public void add(Character c){
        if (_gameState == GAME_FINISHED) {
            Log.d("GameModel", "Game is finished");
            return;
        }
        if (_currentEquation.size() < MAX_STACK_CAPACITY) {
            _currentEquation.add(c);
            updateState();
        } else {
            Log.d("GameModel", "Equation stack is full");
        }   
    }

    public void remove(){
        if (!_currentEquation.isEmpty()){
            _currentEquation.pop();
            updateState();
        } else {
            Log.d("GameModel", "Equation stack is empty");
        }   
    }

    private String currentEquationToString(){
        StringBuilder equation = new StringBuilder();
        for (Character c : _currentEquation) {
            equation.append(c);
        }
        Log.d("Current equation to string", equation.toString());
        return equation.toString();
    }

    public boolean isMathematicallyCorrect() {
        if (_currentEquation.size() != MAX_STACK_CAPACITY) {
            Log.d("GameModel", "Invalid equation");
            return false;
        }
        return EquationScanner.isEquationValid(currentEquationToString());
    }

    public boolean isGameFinished() {
        return _gameState == GAME_FINISHED;
    }

    public List<List<Pair>> getStates() {
        return _states;
    }

    public boolean isWon() {
        return _win == WON;
    }

    public int getGameStatus(){
        return _win;
    }

    public int getNbTries() {
        return _tries;
    }

    /**
     * We iterate over the current equation and the target equation
     * and check if they are equal
     * @return true if the current equation is equal to the target equation
     */
    public boolean isValid() {
        if (_currentEquation.size() != MAX_STACK_CAPACITY) {
            return false;
        }
        for (int i = 0; i < MAX_STACK_CAPACITY; i++) {
            if (_currentEquation.get(i) != _targetEquation.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the current equation by checking the correctness of the equation
     * and updating the game state accordingly to the number of tries and the target equation
     */
    public void validate() {
        if (isMathematicallyCorrect() && _gameState != GAME_FINISHED) {
            List<Pair> state = new ArrayList<>();
            for (int i = 0; i < MAX_STACK_CAPACITY; i++) {
                char content = _currentEquation.get(i);
                char target = _targetEquation.get(i);
                if (content == target) {
                    state.add(new Pair(COLOR_VALID, content));
                } else if (content == ' ') {
                    state.add(new Pair(COLOR_BLANK, content));
                } else {
                    if (_targetEquation.contains(content)) {
                        state.add(new Pair(COLOR_MISPLACED, content));
                    } else {
                        state.add(new Pair(COLOR_INVALID, content));
                    }
                }
            }
            
            _states.set(_tries, state);
            _tries++;
            if (isValid()){
                _gameState = GAME_FINISHED;
                _win = WON;
            }
            if (_tries == MAX_TRIES) {
                _gameState = GAME_FINISHED;
            } 
            if (_gameState == GAME_FINISHED && _win != WON) {
                _win = LOST;
            }   
            _currentEquation.clear(); 

        }
    }
}
