package com.example.pennychet.ui;

public class ListData {
    String type;
    String category;
    String date;
    String account;
    String description;
    double sum;
    int imgid;
    int colorid;

    public ListData(String type,
                    String category,
                    String account,
                    String date,
                    String description,
                    double sum,
                    int imgid,
                    int colorid)
    {
        this.category = category;
        this.date = date;
        this.account = account;
        this.description = description;
        this.sum = sum;
        this.imgid = imgid;
        this.colorid = colorid;
    }
}
