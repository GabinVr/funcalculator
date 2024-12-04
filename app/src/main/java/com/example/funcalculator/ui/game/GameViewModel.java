package com.example.funcalculator.ui.game;

import java.util.List;

import com.example.funcalculator.model.game.GameModel;
import com.example.funcalculator.model.pair.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

public class GameViewModel extends ViewModel {
    private GameModel _gameModel;
    private MutableLiveData<List<List<Pair>>> _statesLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> _gameStatusLiveData = new MutableLiveData<>();

    public LiveData<List<List<Pair>>> getStatesLiveData() {
        return _statesLiveData;
    }
    
    public void updateStatesLiveData() {
        _statesLiveData.postValue(_gameModel.getStates());
    }

    public LiveData<Integer> getGameStatusLiveData() {
        return _gameStatusLiveData;
    }

    public void updateGameStatusLiveData() {
        _gameStatusLiveData.postValue(_gameModel.getGameStatus());
    }

    public GameViewModel() {
        _gameModel = new GameModel("2+2=4");
    }

    public void numberButtonHandler(int buttonId){
        Log.d("GameViewModel", "numberButtonHandler called with buttonId: " + buttonId);
        try{
            switch (buttonId){
                case 0:
                    _gameModel.add('0');
                    break;
                case 1:
                    _gameModel.add('1');
                    break;
                case 2:
                    _gameModel.add('2');
                    break;
                case 3:
                    _gameModel.add('3');
                    break;
                case 4:
                    _gameModel.add('4');
                    break;
                case 5:
                    _gameModel.add('5');
                    break;
                case 6:
                    _gameModel.add('6');
                    break;
                case 7:
                    _gameModel.add('7');
                    break;
                case 8:
                    _gameModel.add('8');
                    break;
                case 9:
                    _gameModel.add('9');
                    break;
                default:
                    break;
            }
        updateStatesLiveData();
        updateGameStatusLiveData();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void operationButtonHandler(Character operator){
        try{
            _gameModel.add(operator);
            updateStatesLiveData();
            updateGameStatusLiveData();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void operationButtonHandler(String operator){
        if (operator.length() == 1){
            operationButtonHandler(operator.charAt(0));
        }
    }

    public void validateButtonHandler(){
        try{
            _gameModel.validate();
            updateStatesLiveData();
            updateGameStatusLiveData();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteButtonHandler(){
        try{
            _gameModel.remove();
            updateStatesLiveData();
            updateGameStatusLiveData();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}