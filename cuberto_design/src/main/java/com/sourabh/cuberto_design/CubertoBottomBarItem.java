package com.sourabh.cuberto_design;

public class CubertoBottomBarItem{
    private int position;
    private String name;
    private int imageRes;

    public CubertoBottomBarItem(int position, String name, int imageRes){
        this.position = position;
        this.name = name;
        this.imageRes = imageRes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}