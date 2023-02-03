package com.example.pennychet.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "account")
    public String account;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "sum")
    public double sum;

    @ColumnInfo(name = "icon")
    public int icon;

    @ColumnInfo(name = "color")
    public int color;
}
