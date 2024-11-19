package com.example.funcalculator.model.game;

import java.util.Stack;

public class GameModel {
    private int _game_state = 0;
    private Stack<Character> _equation;
    private final int _max_stack_size;
    private Stack<Character> _valid_equation;
    public GameModel(int max_stack_size, String valid_equation){
        _max_stack_size = max_stack_size;
        _valid_equation = toEquation(valid_equation);
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

    public void addOperation(Character operation){
        if (_equation.size() <= _max_stack_size) {
            _equation.add(operation);
        }
    }

    public void removeOperation(){
        if (!_equation.isEmpty()){
            _equation.pop();
        }
    }

    public LiteralState validate(){
        LiteralState state = new LiteralState();
        if (_equation.size() == _max_stack_size){
            _game_state = 1;
            for (int i = 0; i <= _max_stack_size; i++){
                Character userLiteral = _equation.pop();
                Character validLiteral = _valid_equation.pop();
                if (userLiteral == validLiteral){
                    state.modifyStateOf(userLiteral, 1);
                }
                if (userLiteral != validLiteral && _valid_equation.contains(userLiteral)){
                    state.modifyStateOf(userLiteral, 2);
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
