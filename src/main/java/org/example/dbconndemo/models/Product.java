package org.example.dbconndemo.models;

public class Product {

    private int id_product;
    private String name;
    private String color;
    private String description;
    private Double price;
    private int quantity;
    private int category_id;


    private String category_Name;
    private String image;

    Category category;

    public Product() {
    }

    public Product(String name, String color, String description, Double price, int quantity, int category_id, String image, Category category) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
        this.image = image;
        this.category = category;
    }

    public Product(String name, String color, String description, Double price, int quantity, String category_Name, String image, Category category) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category_Name = category_Name;
        this.image = image;
        this.category = category;
    }

    public Product(int id_product, String name, Double price, int category_id) {
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.category_id = category_id;
    }


    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId(int id) {
        this.id_product = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Category getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
