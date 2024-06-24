package com.example.glowguide.controller;

import com.example.glowguide.model.HarmfulIngredient;
import com.example.glowguide.service.HarmfulIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/harmful-ingredients")
public class HarmfulIngredientController {
    private final HarmfulIngredientService harmfulIngredientService;

    @Autowired
    public HarmfulIngredientController(HarmfulIngredientService harmfulIngredientService) {
        this.harmfulIngredientService = harmfulIngredientService;
    }

    @GetMapping("/inci-names")
    public List<String> getAllInciNames() {
        return harmfulIngredientService.getAllInciNames();
    }

    @GetMapping("/all")
    public List<HarmfulIngredient> getAllHarmfulIngredients() {
        return harmfulIngredientService.getAllHarmfulIngredients();
    }

    @GetMapping("/by-inci-name")
    public HarmfulIngredient getHarmfulIngredientByInciName(@RequestParam String inciName) {
        return harmfulIngredientService.getHarmfulIngredientByInciName(inciName);
    }


}
