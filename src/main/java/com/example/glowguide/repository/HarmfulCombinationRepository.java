package com.example.glowguide.repository;

import com.example.glowguide.model.HarmfulCombination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarmfulCombinationRepository extends MongoRepository<HarmfulCombination, String> {
}
