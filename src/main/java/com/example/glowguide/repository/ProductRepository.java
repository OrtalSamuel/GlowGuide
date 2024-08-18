package com.example.glowguide.repository;
import com.example.glowguide.model.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface  ProductRepository extends MongoRepository<Product, String> {
   // @Query(value = "{}", fields = "{type : 1}")
    @Aggregation("{ $group: { _id: '$type' } }")
    List<String> findDistinctTypes();
   // @Query(value = "{}", fields = "{type : 1}")
    //List<Product> findDistinctByType();
    List<Product> findByType(String type);
    void deleteByType(String type);

    List<Product> findAll();

    @Query("{ 'ingredients': { $regex: ?0, $options: 'i' } }")
    List<Product> findByIngredientsContaining(String ingredient);


    Optional<Product> findByName(String name);

    Optional<Product> findById(String id);
}
