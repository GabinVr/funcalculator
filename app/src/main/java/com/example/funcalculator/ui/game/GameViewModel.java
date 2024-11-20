package com.example.funcalculator.ui.game;

import java.util.LinkedList;

import com.example.funcalculator.model.game.GameModel;

import androidx.lifecycle.ViewModel;

import android.util.Log;

public class GameViewModel extends ViewModel {
    private int tryCount = 1;
    private GameModel _gameModel;
    private LinkedList[] _states;
    private int _game_state = 0;

    public GameViewModel() {
        _gameModel = new GameModel(5, "2+2=4");
        _states = new LinkedList[4];
    }

    public boolean gameFinished(){
        return _game_state == 1;
    }

    public int winGame(){
        if (tryCount > 4){
            _game_state = 1;

        }
        for (LinkedList<Integer> state : _states){
            if (state == null){
                return 0;
            }
            Log.d("GameViewModel", "state: " + state);
            for (Integer color : state){
                Log.d("GameViewModel", "color: " + color);
                if (color != 1){
                    return 0;
                }
            }
            _game_state = 1;
            return 1;
        }
        return 1;

    }

    public Character[] getExpression(){
        return _gameModel.getExpression();
    }

    @SuppressWarnings("unchecked")
    public LinkedList<Integer>[] getStates(){
        return _states;
    }

    public void numberButtonHandler(int buttonId){
        Log.d("GameViewModel", "numberButtonHandler called with buttonId: " + buttonId);
        try{
            switch (buttonId){
                case 0:
                    _gameModel.addLiteral('0');
                    break;
                case 1:
                    _gameModel.addLiteral('1');
                    break;
                case 2:
                    _gameModel.addLiteral('2');
                    break;
                case 3:
                    _gameModel.addLiteral('3');
                    break;
                case 4:
                    _gameModel.addLiteral('4');
                    break;
                case 5:
                    _gameModel.addLiteral('5');
                    break;
                case 6:
                    _gameModel.addLiteral('6');
                    break;
                case 7:
                    _gameModel.addLiteral('7');
                    break;
                case 8:
                    _gameModel.addLiteral('8');
                    break;
                case 9:
                    _gameModel.addLiteral('9');
                    break;
        }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void operationButtonHandler(Character operator){
        try{
            _gameModel.addLiteral(operator);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void validateButtonHandler(){
        try{
            LinkedList<Integer> state = _gameModel.validate();
            _states[tryCount-1] = state;
            tryCount += 1;
            _gameModel = new GameModel(5, "2+2=4");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteButtonHandler(){
        try{
            _gameModel.removeOperation();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getTryCount(){
        return tryCount;
    }
}