package com.example.funcalculator.ui.calculator;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.funcalculator.model.calculator.CalculatorModel;
import com.example.funcalculator.model.expression.Expression;
import com.example.funcalculator.data.remote.apis.evaluator.VolleyClient;
import com.example.funcalculator.data.repository.CalculatorRepository;

public class CalculatorViewModel extends ViewModel {
    private final Expression _expression;
    private CalculatorModel _model = new CalculatorModel();
    private final MutableLiveData<String> _screen = new MutableLiveData<>();
    private CalculatorRepository _repository;
    private VolleyClient _volleyClient; 

    public CalculatorViewModel() {
        _expression = new Expression("42");
        _screen.setValue(_expression.getValue());
        Log.d("CalculatorViewModel", "CalculatorViewModel created");
    }


    public void setVolleyClient(VolleyClient volleyClient) {
        _volleyClient = volleyClient;
        if (_volleyClient == null) {
            throw new IllegalArgumentException("VolleyClient cannot be null");
        }
        _repository = new CalculatorRepository(_volleyClient);
    }

    public void onEqualButtonClicked() {
    
        Log.d("CalculatorViewModel", "onEqualButtonClicked");
        try {
            _repository.evaluateExpression(_expression,
            response -> {
                String value = null;
                try {
                    value = response.getString("result");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Log.d("CalculatorViewModel", "Response received: " + value);
                _screen.postValue(value);
            }
            , error -> {
                Log.e("CalculatorViewModel", "Error while evaluating expression", error);
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 404) {
                            Log.e("API_ERROR", "Endpoint not found (404)");
                        } else {
                            Log.e("API_ERROR", "HTTP error code: " + statusCode);
                        }
                } else {
                    Log.e("API_ERROR", "Network error: " + error.getMessage());
                }
            });
        }
        catch (Exception e) {
            _screen.postValue("Error");
        }
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