package com.example.funcalculator.model.game;

import android.util.Log;

import java.util.LinkedList;
import java.util.Stack;

public class GameModel {
    private int _game_state = 0;
    private Stack<Character> _equation;
    private final int _max_stack_size;
    private Stack<Character> _valid_equation;

    public GameModel(int max_stack_size, String valid_equation){
        Log.d("GameModel", "Constructor called with max_stack_size: " + max_stack_size + " and valid_equation: " + valid_equation);
        _max_stack_size = max_stack_size;
        _valid_equation = toEquation(valid_equation);
        _equation = new Stack<>();
    };

    public Stack<Character> toEquation(String equation){
        if (equation.length() == _max_stack_size){
            Stack<Character> stackEquation = new Stack<>();
            for (int i=0; i<equation.length(); i++){
                stackEquation.add(equation.charAt(i));
            }
            return stackEquation;
        }
        throw new IllegalArgumentException("Invalid equation");
    }

    public void addLiteral(Character operation){
        if (_equation.size() < _max_stack_size) {
            _equation.add(operation);
        }
    }

    public void removeOperation(){
        if (!_equation.isEmpty()){
            _equation.pop();
        }
    }

    public Character[] getExpression(){

        Character[] expression = new Character[_max_stack_size];
        for (int i = 0; i < _max_stack_size; i++){
            if (_equation.size() > i){
                expression[i] = _equation.get(i);
            }
            else{
                expression[i] = ' ';
            }
        }
        return expression;

    }

    public LinkedList<Integer> validate(){
        LinkedList<Integer> state = new LinkedList<Integer>();
        @SuppressWarnings("unchecked")
        Stack<Character> valid_equation_copy = (Stack<Character>) _valid_equation.clone();
        for (int i = 0; i < _max_stack_size; i++){
            state.add(0);
        }
        if (_equation.size() == _max_stack_size){
            _game_state = 1;
            for (int i = 0; i < _max_stack_size; i++){
                Character userLiteral = _equation.pop();
                Character validLiteral = valid_equation_copy.pop();
                if (userLiteral == validLiteral){
                    state.set(_max_stack_size - 1 - i , 1);
                }
                if (userLiteral != validLiteral && _valid_equation.contains(userLiteral)){
                    state.set(_max_stack_size - 1 - i , 2);
                    _game_state = 0;
                }
                if (userLiteral != validLiteral){
                    _game_state = 0;
                }
            }
        }
        return state;
    }

    public int get_game_state() {
        return _game_state;
    }
}
