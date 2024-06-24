package com.example.glowguide.service;


import com.example.glowguide.model.HarmfulIngredient;
import com.example.glowguide.model.Product;
import com.example.glowguide.repository.HarmfulIngredientRepository;
import com.example.glowguide.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final HarmfulIngredientRepository harmfulIngredientRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, HarmfulIngredientRepository harmfulIngredientRepository) {
        this.productRepository = productRepository;
        this.harmfulIngredientRepository = harmfulIngredientRepository;
    }

    public List<Product> getProductsWithHarmfulIngredients() {
        List<HarmfulIngredient> harmfulIngredients = harmfulIngredientRepository.findAll();
        Set<String> harmfulInciNames = harmfulIngredients.stream()
                .map(harmfulIngredient -> stripSpecialCharacters(harmfulIngredient.getInciName()))
                .collect(Collectors.toSet());

        return productRepository.findAll().stream()
                .peek(product -> {
                    List<String> ingredients = Arrays.asList(product.getIngredients().split(","));
                    List<String> harmfulIngredientsInProduct = ingredients.stream()
                            .map(this::stripSpecialCharacters)
                            .filter(harmfulInciNames::contains)
                            .collect(Collectors.toList());
                    product.setHarmfulIngredients(harmfulIngredientsInProduct);
                    logger.info("Product {} contains harmful ingredients: {}", product.getName(), harmfulIngredientsInProduct);
                })
                .filter(product -> !product.getHarmfulIngredients().isEmpty())
                .collect(Collectors.toList());
    }


    // Helper method to strip special characters from a string
    private String stripSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }

    public Set<String> getHarmfulIngredients(String ingredientsString) {
        List<HarmfulIngredient> harmfulIngredients = harmfulIngredientRepository.findAll();
        Set<String> harmfulInciNames = harmfulIngredients.stream()
                .map(harmfulIngredient -> stripSpecialCharacters(harmfulIngredient.getInciName()))
                .collect(Collectors.toSet());

        return Arrays.asList(ingredientsString.split(",")).stream()
                .map(this::stripSpecialCharacters)
                .filter(harmfulInciNames::contains)
                .collect(Collectors.toSet());
    }


    public List<String> getAllProductTypes() {
        List<Product> products = productRepository.findDistinctByType();
        return products.stream()
                .map(Product::getType)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public void deleteProductsByType(String type) {
        productRepository.deleteByType(type);
    }
    public List<Product> getProductsByIngredient(String ingredient) {
        return productRepository.findAll().stream()
                .filter(product -> {
                    if (product.getIngredients() == null || product.getIngredients().isEmpty()) {
                        return false;
                    }
                    List<String> productIngredients = Arrays.asList(product.getIngredients().split(","));
                    return productIngredients.contains(ingredient.trim());
                })
                .collect(Collectors.toList());
    }

}
