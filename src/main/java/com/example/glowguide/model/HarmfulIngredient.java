package com.example.glowguide.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "harmful ingredients")
public class HarmfulIngredient {
    @Id
    private String id;

    @Field("INCI Name of substance / Theme")
    private String inciName; // Corresponds to "INCI Name of substance / Theme" in MongoDB

    @Field("CAS #")
    private String casNumber; // Corresponds to "CAS #" in MongoDB

    @Field("Action needed")
    private String actionNeeded; // Corresponds to "Action needed" in MongoDB

    @Field("Application date")
    private String applicationDate;

    @Field("More information")
    private String moreInformation;

    @Field("Categories")
    private String categories;

    public HarmfulIngredient() {}

    // Constructors, getters, and setters
    public HarmfulIngredient(String id, String inciName, String casNumber, String actionNeeded, String applicationDate, String moreInformation, String categories) {
        this.id = id;
        this.inciName = inciName;
        this.casNumber = casNumber;
        this.actionNeeded = actionNeeded;
        this.applicationDate = applicationDate;
        this.moreInformation = moreInformation;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInciName() {
        return inciName;
    }

    public void setInciName(String inciName) {
        this.inciName = inciName;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getActionNeeded() {
        return actionNeeded;
    }

    public void setActionNeeded(String actionNeeded) {
        this.actionNeeded = actionNeeded;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
