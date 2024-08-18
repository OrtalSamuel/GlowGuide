package com.example.glowguide.service;

import com.example.glowguide.model.HarmfulCombination;
import com.example.glowguide.repository.HarmfulCombinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HarmfulCombinationService {

    @Autowired
    private HarmfulCombinationRepository harmfulCombinationRepository;

    public List<HarmfulCombination> getAllHarmfulCombinations() {
        return harmfulCombinationRepository.findAll();
    }

    public Map<String, Set<String>> getHarmfulCombinations() {
        List<HarmfulCombination> harmfulCombinationsList = harmfulCombinationRepository.findAll();

        // Convert the list to a map
        return harmfulCombinationsList.stream().collect(Collectors.toMap(
                HarmfulCombination::getId,
                harmfulCombination -> Set.copyOf(harmfulCombination.getHarmfulWith())
        ));
    }


    public HarmfulCombination addHarmfulCombination(HarmfulCombination harmfulCombination) {
        return harmfulCombinationRepository.save(harmfulCombination);
    }

    public List<HarmfulCombination> addAHarmfulCombinations(List<HarmfulCombination> combinations) {
        return harmfulCombinationRepository.saveAll(combinations);
    }
}
