package com.example.funcalculator.model.expression;

public class Expression {
    private String _value;
    public Expression(){
        _value = "";
    }
    public Expression(String value) {
        _value = value;
    }
    public void add(String value) {
        _value += value;
    }
    public void clear() {
        _value = "";
    }

    public void changeSign() {
        if (_value.startsWith("-")) {
            _value = _value.substring(1);
        } else {
            _value = "-" + _value;
        }
    }

    public void percent() {
        _value = "(" + _value + ") / 100";
    }

    public String getValue() {
        return _value;
    }

    public String toJson(){
        return "{\"expression\":\"" + _value + "\"}";
    }

    public void setExpression(String response) {
        _value = response;
    }
}