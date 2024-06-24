package com.example.glowguide;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnectionTest {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017/GlowGuide"; // or your MongoDB Atlas URI

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("your_database_name");
            System.out.println("Connected to the database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}