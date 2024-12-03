package com.example.funcalculator.model.pair;

public class Pair {
    private final int color;
    private final Character content;

    public Pair(int color, Character content) {
        this.color = color;
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public Character getContent() {
        return content;
    }
}