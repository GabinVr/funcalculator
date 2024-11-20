package com.example.funcalculator.ui.calculator;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funcalculator.model.expression.Expression;

public class CalculatorViewModel extends ViewModel {
    private final Expression _expression;
    private final MutableLiveData<String> _screen = new MutableLiveData<>();
    public CalculatorViewModel() {
        _expression = new Expression("42");
        _screen.setValue(_expression.getValue());
        Log.d("CalculatorViewModel", "CalculatorViewModel created");
    }

    public void onEqualButtonClicked() {
        // Implementation of the calculation
        Log.d("CalculatorViewModel", "onEqualButtonClicked");
         _screen.postValue("0");
         _expression.clear();
    }
 
     public void onNumberButtonClicked(String number) {
         _expression.add(number);
         _screen.postValue(_expression.getValue());
     }
 
     public void onOperatorButtonClicked(String operator) {
         _expression.add(operator);
         _screen.postValue(_expression.getValue());
     }
 
     public void onClearButtonClicked() {
         _expression.clear();
         _screen.postValue(_expression.getValue());
     }
 
     public void onChangeSignButtonClicked() {
         _expression.changeSign();
         _screen.postValue(_expression.getValue());
     }
 
     public void onDotButtonClicked() {
         _expression.add(".");
         _screen.postValue(_expression.getValue());
     }
 
     public void onPercentButtonClicked() {
         _expression.percent();
         _screen.postValue(_expression.getValue());
     }
 
     public MutableLiveData<String> getScreen() {
         return _screen;
     }

}