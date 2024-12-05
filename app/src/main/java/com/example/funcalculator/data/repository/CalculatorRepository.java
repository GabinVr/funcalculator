package com.example.funcalculator.data.repository;


import com.android.volley.Response;
import com.example.funcalculator.data.remote.apis.evaluator.VolleyClient;
import com.example.funcalculator.model.expression.Expression;
import org.json.JSONObject;
import android.util.Log;


public class CalculatorRepository {
    private final VolleyClient volleyClient;

    public CalculatorRepository(VolleyClient volleyClient) {
        this.volleyClient = volleyClient;
    }

    public void evaluateExpression(Expression expression, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        Log.d("CalculatorRepository", "evaluateExpression called with expression"+expression.getValue());
        volleyClient.postExpression(expression, successListener, errorListener);
    }
}
