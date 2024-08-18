package com.example.glowguide.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "routines")
public class Routine {

    @Id
    private String routineId;
    private String createdTimeStamp;
    private Map<String, List<String>> chosenProducts;
    private Map<String, List<String>> recommendationsProducts;
    private String userId; // Reference to the user

    // Constructors
    public Routine(String routineId, String createdTimeStamp, Map<String, List<String>> chosenProducts, Map<String, List<String>> recommendationsProducts, String userId) {
        this.routineId = routineId;
        this.createdTimeStamp = createdTimeStamp;
        this.chosenProducts = chosenProducts;
        this.recommendationsProducts = recommendationsProducts;
        this.userId = userId;
    }

    public Routine() {
        this.chosenProducts = new HashMap<>();
        this.recommendationsProducts = new HashMap<>();
    }

    public Routine(String routineId, Map<String, List<String>> chosenProducts) {
        this.routineId = routineId;
        this.chosenProducts = chosenProducts;
    }

    // Getters and Setters
    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Map<String, List<String>> getChosenProducts() {
        return chosenProducts;
    }

    public void setChosenProducts(Map<String, List<String>> chosenProducts) {
        this.chosenProducts = chosenProducts;
    }

    public Map<String, List<String>> getRecommendationsProducts() {
        return recommendationsProducts;
    }

    public void setRecommendationsProducts(Map<String, List<String>> recommendationsProducts) {
        this.recommendationsProducts = recommendationsProducts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
