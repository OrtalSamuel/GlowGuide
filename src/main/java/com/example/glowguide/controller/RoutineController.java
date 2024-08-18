package com.example.glowguide.controller;

import com.example.glowguide.model.Routine;
import com.example.glowguide.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @GetMapping("/all")
    public List<Routine> getAllRoutines() {
        return routineService.getAllRoutines();
    }

    @PostMapping("/add")
    public Routine addRoutine(@RequestBody Routine routine) {
        return routineService.addRoutine(routine);
    }

    @PostMapping("/addProductToRoutine")
    public ResponseEntity<String> addProductToRoutine(@RequestParam String userId, @RequestParam String productId) {
        String result = routineService.addProductToRoutine(userId, productId);
        return ResponseEntity.ok(result);
    }

}