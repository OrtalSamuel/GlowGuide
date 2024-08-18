package com.example.glowguide.repository;


import com.example.glowguide.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
