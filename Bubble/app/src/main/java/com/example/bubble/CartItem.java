package com.example.bubble;

public class CartItem {
    private String id;
    private String name;
    private String date;
    private String imageUrl;
    private int quantity;

    public CartItem() {

    }

    public CartItem(String name, String date, String imageUrl, int quantity) {
        this.name = name;
        this.date = date;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}



