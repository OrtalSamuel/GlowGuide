package com.example.glowguide.repository;

import com.example.glowguide.model.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {
}