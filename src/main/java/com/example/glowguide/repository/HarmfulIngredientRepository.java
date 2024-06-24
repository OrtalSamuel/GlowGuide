package com.example.glowguide.repository;

import com.example.glowguide.model.HarmfulIngredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarmfulIngredientRepository extends MongoRepository<HarmfulIngredient, String> {
    HarmfulIngredient findByInciName(String inciName);
    List<HarmfulIngredient> findAll();
}

