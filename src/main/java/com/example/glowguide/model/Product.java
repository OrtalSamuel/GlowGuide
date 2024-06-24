package com.example.glowguide.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "Products")
public class Product {
    @Id
    private String id;
    private String brand;
    private String name;
    private String type;
    private String country;
    @Field("ingridients")
    private String ingredients;
    private String afterUse;

    private List<String> harmfulIngredients; // New field for harmful ingredients

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAfterUse() {
        return afterUse;
    }

    public void setAfterUse(String afterUse) {
        this.afterUse = afterUse;
    }

    public void setHarmfulIngredients(List<String> harmfulIngredients) {
        this.harmfulIngredients = harmfulIngredients;
    }
    public List<String> getHarmfulIngredients() {
        return harmfulIngredients;
    }
}
