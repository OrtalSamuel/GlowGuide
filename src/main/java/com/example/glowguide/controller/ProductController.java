package com.example.glowguide.controller;

import com.example.glowguide.model.Product;
import com.example.glowguide.repository.ProductRepository;
import com.example.glowguide.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/types")
    public List<String> getAllProductTypes() {
        List<String> types = productService.getAllProductTypes();
        logger.info("Distinct Product Types: {}", types);
        return types;
    }
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
       // System.out.println("All Products: " + products);
        return productService.getAllProducts();
    }

    @GetMapping("/by-type")
    public List<Product> getProductsByType(@RequestParam String type) {
        List<Product> products = productService.getProductsByType(type);
        logger.info("Products of type {}: {}", type, products);
        return products;
    }

    @DeleteMapping("/by-type")
    public void deleteProductsByType(@RequestParam String type) {
        productService.deleteProductsByType(type);
        System.out.println("Deleted products of type: " + type);
    }
    @GetMapping("/by-ingredient")
    public List<Product> getProductsByIngredient(@RequestParam String ingredient) {
        List<Product> products = productService.getProductsByIngredient(ingredient);
        System.out.println("Products with ingredient " + ingredient + ": " + products);
        return products;
    }

    @GetMapping("/products-with-harmful-ingredients")
    public ResponseEntity<List<Product>> getProductsWithHarmfulIngredients() {
        try {
            List<Product> products = productService.getProductsWithHarmfulIngredients();
            logger.info("Products with harmful ingredients: {}", products);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error getting products with harmful ingredients", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/harmful-ingredients")
    public ResponseEntity<Set<String>> getHarmfulIngredients(@RequestBody String ingredientsString) {
        try {
            Set<String> harmfulIngredients = productService.getHarmfulIngredients(ingredientsString);
            logger.info("Harmful ingredients found: {}", harmfulIngredients);
            return ResponseEntity.ok(harmfulIngredients);
        } catch (Exception e) {
            logger.error("Error getting harmful ingredients", e);
            return ResponseEntity.status(500).body(null);
        }
    }
    }



