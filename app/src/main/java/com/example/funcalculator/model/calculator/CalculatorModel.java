package com.example.funcalculator.model.calculator;

import com.example.funcalculator.model.expression.Expression;
import com.example.funcalculator.data.remote.apis.evaluator.EvaluatorClient;

public class CalculatorModel {
    // Use live data in repository to prevent ui from being blocked
    public String evaluate(Expression expression){
        String jsonExpression = expression.toJson();
        EvaluatorClient client = new EvaluatorClient();
        client.callEvaluator(jsonExpression);
        return client.getResponse();
    }
}
