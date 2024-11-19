package com.example.funcalculator.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equation {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "equation")
    public String equation;
}
