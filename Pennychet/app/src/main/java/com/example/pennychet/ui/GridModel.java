package com.example.pennychet.ui;

public class GridModel {

    private String category_name;
    private int sum;
    private int imgid;
    private int colorid;

    public GridModel(String category_name, int imgid, int colorid, int sum) {
        this.category_name = category_name;
        this.imgid = imgid;
        this.colorid = colorid;
        this.sum = sum;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getColorId() {
        return colorid;
    }

    public void setColorId(int colorid) {
        this.colorid = colorid;
    }

    public String getSum() {
        return String.valueOf(sum);
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

