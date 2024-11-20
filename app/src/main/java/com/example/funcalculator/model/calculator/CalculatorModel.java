package com.example.funcalculator.model.calculator;

import com.example.funcalculator.model.expression.Expression;
import com.example.funcalculator.data.remote.apis.evaluator.EvaluatorClient;

public class CalculatorModel {

    public CalculatorModel(){

    }

    public static String evaluate(Expression expression){
        String jsonExpression = expression.toJson();
        EvaluatorClient client = new EvaluatorClient();
        client.callEvaluator(jsonExpression);
        return client.getResponse();
    }
}
