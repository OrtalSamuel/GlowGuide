package com.example.glowguide.service;


import com.example.glowguide.model.HarmfulIngredient;
import com.example.glowguide.model.Product;
import com.example.glowguide.repository.HarmfulIngredientRepository;
import com.example.glowguide.repository.ProductRepository;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
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

    // Method to check for all harmful ingredients in a product
    public Optional<String> checkForHarmfulIngredients(Product product) {
        List<HarmfulIngredient> harmfulIngredients = harmfulIngredientRepository.findAll();
        Set<String> harmfulInciNames = harmfulIngredients.stream()
                .map(harmfulIngredient -> stripSpecialCharacters(harmfulIngredient.getInciName()))
                .collect(Collectors.toSet());

        List<String> productIngredients = Arrays.asList(product.getIngredients().split(","));

        for (String ingredient : productIngredients) {
            String strippedIngredient = stripSpecialCharacters(ingredient);
            if (harmfulInciNames.contains(strippedIngredient)) {
                logger.info("Harmful ingredient found: {}", strippedIngredient);
                return Optional.of(strippedIngredient);
            }
        }

        return Optional.empty();
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
        List<String> types = productRepository.findDistinctTypes();
        return types;
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


//    public List<Product> getTop3ProductsByIngredientList(List<String> ingredientList) {
//        List<Product> allProducts = productRepository.findAll();
//        Map<Product, Double> productScores = new HashMap<>();
//
//        for (Product product : allProducts) {
//            if(product.getIngredients()!= null){ // added for testing
//            List<String> productIngredients = Arrays.asList(product.getIngredients().split(","));
//            double score = calculateCosineSimilarity(ingredientList, productIngredients);
//            productScores.put(product, score); }
//        }
//
//        return productScores.entrySet().stream()
//                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
//                .limit(3)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }



//public List<Product> getTopProductsByIngredientList(List<String> ingredientList) {
//    // Get all unique product types
//    List<String> productTypes = productRepository.findDistinctTypes();
//    System.out.println("length of categories " + productTypes.toArray().length);
//    // Initialize a map to hold top products for each type
//    List<Product> topProducts = new ArrayList<>();
//
//    // For each type, get all products and calculate scores
//    for (String type : productTypes) {
//        List<Product> productsByType = productRepository.findByType(type);
//        System.out.println("length of products by type " +type+" "+ productsByType.toArray().length);
//
//        List<Product> topProductsForType = getTopProductsForType(productsByType, ingredientList, 6);
//        System.out.println("length of top products " + topProductsForType.toArray().length);
//
//        topProducts.addAll(topProductsForType);
//    }
//
//    // Sort the combined list of top products
//    //topProducts.sort((p1, p2) -> Double.compare(
//      //      calculateCosineSimilarity(ingredientList, Arrays.asList(p2.getIngredients().split(","))),
//        //    calculateCosineSimilarity(ingredientList, Arrays.asList(p1.getIngredients().split(",")))
//   // ));
//
//    return  topProducts;
//}




//    private List<Product> getTopProductsForType(List<Product> products, List<String> ingredientList, int limit) {
//        Map<Product, Double> productScores = new HashMap<>();
//
//        for (Product product : products) {
//            if (product.getIngredients() != null) {
//                List<String> productIngredients = Arrays.asList(product.getIngredients().split(","));
//                double score = calculateCosineSimilarity(ingredientList, productIngredients);
//                productScores.put(product, score);
//            }
//        }
//
//        return productScores.entrySet().stream()
//                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
//                .limit(limit)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }







//    private double calculateCosineSimilarity(List<String> list1, List<String> list2) {
//        Map<CharSequence, Integer> vectorA = list1.stream()
//                .collect(Collectors.toMap(ingredient -> ingredient, ingredient -> 1, Integer::sum));
//        Map<CharSequence, Integer> vectorB = list2.stream()
//                .collect(Collectors.toMap(ingredient -> ingredient, ingredient -> 1, Integer::sum));
//
//        CosineSimilarity cosineSimilarity = new CosineSimilarity();
//        return cosineSimilarity.cosineSimilarity(vectorA, vectorB);
//    }



    public List<Product> getTop6SimilarProducts(List<String> inputIngredients) {
        return getTopProductsByIngredientList(inputIngredients);
    }



    /////NEWEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

    // Method to preprocess ingredients and store vectors in the database
    public void preprocessIngredients() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getIngredients() != null) {
                // Split the ingredients string into a list
                List<String> ingredients = Arrays.asList(product.getIngredients().split(","));
                // Convert the list into a vector (map of ingredient to count)
                Map<CharSequence, Integer> ingredientVector = ingredients.stream()
                        .collect(Collectors.toMap(ingredient -> ingredient, ingredient -> 1, Integer::sum));
                // Set the ingredient vector in the product
                product.setIngredientVector(ingredientVector);
                // Save the product back to the repository
                productRepository.save(product);
            }
        }
    }


    // Method to get top products by ingredient list
    public List<Product> getTopProductsByIngredientList(List<String> ingredientList) {
        List<String> productTypes =  productRepository.findDistinctTypes();
        List<Product> topProducts = new ArrayList<>();
        Map<CharSequence, Integer> queryVector = ingredientList.stream()
                .collect(Collectors.toMap(ingredient -> ingredient, ingredient -> 1, Integer::sum));

        for (String type : productTypes) {
            List<Product> productsByType = productRepository.findByType(type);
            List<Product> topProductsForType = getTopProductsForType(productsByType,  queryVector, 6);
            topProducts.addAll(topProductsForType);
        }

        return  topProducts;
    }





    // Helper method to get top products for a specific type
    private List<Product> getTopProductsForType(List<Product> products, Map<CharSequence, Integer> queryVector, int limit) {
        Map<Product, Double> productScores = new HashMap<>();

        for (Product product : products) {
            if (product.getIngredientVector() != null) {
                double score = calculateCosineSimilarity(queryVector, product.getIngredientVector());
                productScores.put(product, score);
            }
        }

        return productScores.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }







    // Method to calculate cosine similarity
    private double calculateCosineSimilarity(Map<CharSequence, Integer> vectorA, Map<CharSequence, Integer> vectorB) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        return cosineSimilarity.cosineSimilarity(vectorA, vectorB);
    }


    public Optional<Product> getProductByName(String name) {
        // Implement this method to interact with the repository and find a product by its name
        return productRepository.findByName(name);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
}
