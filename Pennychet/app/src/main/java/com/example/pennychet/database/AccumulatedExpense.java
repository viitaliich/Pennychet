package com.example.pennychet.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccumulatedExpense {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "sum")
    public double sum;
}
