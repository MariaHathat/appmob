package com.example.appmobtp;


public class Item {
    private String name;
    private String description;
    private String image;
    private String price;

    public Item(String name, String description, String image, String price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
