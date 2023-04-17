package com.example.pennychet.ui;

public class ListData {
    String type;
    public String category;
    public String date;
    public String account;
    public String description;
    public double sum;
    public int imgid;
    public int colorid;

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
