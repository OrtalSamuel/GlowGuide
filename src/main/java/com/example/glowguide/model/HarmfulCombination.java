package com.example.glowguide.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "harmful_combinations")
public class HarmfulCombination {

    @Id
    private String id; // This will be the INCI name or another unique identifier
    private List<String> harmfulWith; // List of ingredients that are harmful with this ingredient


    // Constructors
    public HarmfulCombination() {}

    public HarmfulCombination(String id, List<String> harmfulWith) {
        this.id = id;
        this.harmfulWith = harmfulWith;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHarmfulWith() {
        return harmfulWith;
    }

    public void setHarmfulWith(List<String> harmfulWith) {
        this.harmfulWith = harmfulWith;
    }
}