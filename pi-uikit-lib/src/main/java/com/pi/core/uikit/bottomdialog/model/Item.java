package com.pi.core.uikit.bottomdialog.model;

/**
 * Created by CC on 2018/3/12.
 */

public class Item {
    private int id;
    private int icon;
    private String name;

    public Item() {
    }
    public Item(String name) {
        this.name = name;

    }
    public Item(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
