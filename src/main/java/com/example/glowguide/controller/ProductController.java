package com.example.glowguide.controller;

import com.example.glowguide.model.Product;
import com.example.glowguide.repository.ProductRepository;
import com.example.glowguide.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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
        System.out.println("All Products: " + products);
        return productService.getAllProducts();
    }

   // "http://localhost:8080/api/products/by-type?type=Shampoo"
    @GetMapping("/by-type")
    public List<Product> getProductsByType(@RequestParam String type) {
        List<Product> products = productService.getProductsByType(type);
        logger.info("Products of type {}: {}", type, products);
        return products;
    }
    //"http://localhost:8080/api/products/by-type?type=Hand%20Care"
    @DeleteMapping("/by-type")
    public void deleteProductsByType(@RequestParam String type) {
        productService.deleteProductsByType(type);
        System.out.println("Deleted products of type: " + type);
    }
   // http://localhost:8080/api/products/by-ingredient?ingredient=Benzyl Alcohol
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

    //get the harmful ingredients
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

    // Endpoint to check for harmful ingredients
    @PostMapping("/check-harmful-ingredients")
    public ResponseEntity<String> checkForHarmfulIngredients(@RequestBody Product product) {
        try {
            Optional<String> harmfulIngredient = productService.checkForHarmfulIngredients(product);
            if (harmfulIngredient.isPresent()) {
                return ResponseEntity.ok("Harmful ingredient found: " + harmfulIngredient.get());
            } else {
                return ResponseEntity.ok("No harmful ingredients found.");
            }
        } catch (Exception e) {
            logger.error("Error checking for harmful ingredients", e);
            return ResponseEntity.status(500).body("An error occurred while checking for harmful ingredients.");
        }
    }



    @PostMapping("/top6")
    public ResponseEntity<List<Product>> getTop3SimilarProducts(@RequestBody  List<String> inputIngredients) {
        try {
         //   List<String> inputIngredients = Arrays.asList(ingredients.split(","));
            if (inputIngredients == null || inputIngredients.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // Return bad request if input is missing or empty
            }

            List<Product> top3Products = productService.getTop6SimilarProducts(inputIngredients);
            return ResponseEntity.ok(top3Products);
        } catch (Exception e) {
            logger.error("Error getting top 3 similar products", e);
            return ResponseEntity.status(500).body(null); // Return internal server error for other exceptions
        }
    }


    @GetMapping("/by-name")
    public ResponseEntity<Product> getProductByName(@RequestParam String name) {
        try {
            Optional<Product> product = productService.getProductByName(name);
            if (product.isPresent()) {
                logger.info("Product found: {}", product.get());
                return ResponseEntity.ok(product.get());
            } else {
                logger.info("No product found with name: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error getting product by name", e);
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/by-id")
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            if (product.isPresent()) {
                logger.info("Product found: {}", product.get());
                return ResponseEntity.ok(product.get());
            } else {
                logger.info("No product found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error getting product by ID", e);
            return ResponseEntity.status(500).body(null);
        }
    }



    }

