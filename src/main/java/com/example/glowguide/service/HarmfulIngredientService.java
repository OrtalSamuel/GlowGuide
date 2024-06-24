package com.example.glowguide.service;

import com.example.glowguide.model.HarmfulIngredient;
import com.example.glowguide.repository.HarmfulIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class HarmfulIngredientService {
    private final HarmfulIngredientRepository harmfulIngredientRepository;

    @Autowired
    public HarmfulIngredientService(HarmfulIngredientRepository harmfulIngredientRepository) {
        this.harmfulIngredientRepository = harmfulIngredientRepository;
    }


    public List<String> getAllInciNames() {
        return harmfulIngredientRepository.findAll().stream()
                .map(HarmfulIngredient::getInciName)
                .collect(Collectors.toList());
    }

    public List<HarmfulIngredient> getAllHarmfulIngredients() {
        return harmfulIngredientRepository.findAll();
    }

    public HarmfulIngredient getHarmfulIngredientByInciName(String inciName) {
        return harmfulIngredientRepository.findByInciName(inciName);
    }
}
