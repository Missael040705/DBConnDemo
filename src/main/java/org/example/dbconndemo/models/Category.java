package org.example.dbconndemo.models;

public class Category {
    private int id_category;
    private String name;
    private String color;
    private String icon;

    public Category() {
    }

    public Category(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public Category(int id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId(int id) {
        this.id_category = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return getName();
    }
}
