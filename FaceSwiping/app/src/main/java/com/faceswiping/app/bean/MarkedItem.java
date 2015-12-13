package com.faceswiping.app.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by John on 15/12/13.
 */
public class MarkedItem implements Serializable {

    private ArrayList<MarkedInnerItem> data;

    private int height;

    private int width;

    private int x;

    private int y;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<MarkedInnerItem> getData() {
        return data;
    }

    public void setData(ArrayList<MarkedInnerItem> data) {
        this.data = data;
    }
}
