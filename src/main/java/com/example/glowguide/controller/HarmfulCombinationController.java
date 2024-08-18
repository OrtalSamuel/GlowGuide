package com.example.glowguide.controller;


import com.example.glowguide.model.HarmfulCombination;
import com.example.glowguide.service.HarmfulCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harmful-combinations")
public class HarmfulCombinationController {

    @Autowired
    private HarmfulCombinationService harmfulCombinationService;

    @GetMapping("/all")
    public List<HarmfulCombination> getAllHarmfulCombinations() {
        return harmfulCombinationService.getAllHarmfulCombinations();
    }

    @PostMapping("/add")
    public HarmfulCombination addHarmfulCombination(@RequestBody HarmfulCombination harmfulCombination) {
        return harmfulCombinationService.addHarmfulCombination(harmfulCombination);
    }

    @PostMapping("/add-bulk")
    public List<HarmfulCombination> addHarmfulCombinations(@RequestBody List<HarmfulCombination> combinations) {
        return harmfulCombinationService.addAHarmfulCombinations(combinations);
    }





}
