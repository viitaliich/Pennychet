package com.example.pennychet.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccumulatedDate {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "sum")
    public double sum;
}
