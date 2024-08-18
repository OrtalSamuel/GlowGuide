package com.example.glowguide.service;

import com.example.glowguide.model.Product;
import com.example.glowguide.model.Routine;
import com.example.glowguide.model.User;
import com.example.glowguide.repository.ProductRepository;
import com.example.glowguide.repository.RoutineRepository;
import com.example.glowguide.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutineService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HarmfulCombinationService harmfulCombinationService;

    private Map<String, Set<String>> harmfulCombinations;

    // Cache to store harmful combinations for products that have already been checked
    private Map<String, Map<String, Set<String>>> productHarmfulCombinationCache = new HashMap<>();

    // Constructor
    public RoutineService(RoutineRepository routineRepository,HarmfulCombinationService harmfulCombinationService,ProductRepository productRepository, UserRepository userRepository) {
        this.routineRepository = routineRepository;
        this.harmfulCombinationService= harmfulCombinationService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Initialize harmfulCombinations after all dependencies have been injected
    @PostConstruct
    public void init() {
        harmfulCombinations = harmfulCombinationService.getHarmfulCombinations();
    }

    public String addProductToRoutine(String userId, String newProductId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found";
        }

        String routineId = user.getRoutineId();
        Routine userRoutine;

        if (routineId == null ) {
            // No routine found, create a new routine
            userRoutine = new Routine();
            userRoutine.setUserId(userId);
            userRoutine = routineRepository.save(userRoutine);


            // Update the user with the new routineId
            user.setRoutineId(userRoutine.getRoutineId());
            userRepository.save(user);
        } else {
            // Retrieve the existing routine
            userRoutine = routineRepository.findById(routineId).orElse(new Routine(routineId, new HashMap<>()));
        }

        Map<String, List<String>> chosenProducts = userRoutine.getChosenProducts();
        System.out.println("chosen Products: " + chosenProducts);

        Product newProduct = productRepository.findById(newProductId).orElse(null);
        if (newProduct == null) {
            return "Product not found";
        }

        String newProductType = newProduct.getType();
        List<String> routineProducts = new ArrayList<>();
        for (List<String> productIds : chosenProducts.values()) {
            routineProducts.addAll(productIds);
        }
        System.out.println("Routine Product IDs: " + routineProducts);
        String ingredientsString = newProduct.getIngredients();
        System.out.println("ingredients String: " + ingredientsString);
        List<String> ingredientsList = Arrays.asList(ingredientsString.split("\\s*,\\s*"));
        System.out.println("ingredients List: " + ingredientsList);
        Set<String> newProductIngredients = new HashSet<>(ingredientsList);


        // Debug: Print new product ingredients
        System.out.println("New Product Ingredients: " + newProductIngredients);

        // Check harmful combinations



        for (String existingProductId : routineProducts) {
            System.out.println("IN For");
            Product existingProduct = productRepository.findById(existingProductId).orElse(null);
            if (existingProduct == null) continue;

            String existingIngredientsString = existingProduct.getIngredients();
            List<String> existingIngredientsList = Arrays.asList(existingIngredientsString.split("\\s*,\\s*"));
            Set<String> existingProductIngredients = new HashSet<>(existingIngredientsList);


            // Debug: Print existing product components
            System.out.println("Existing Product Components: " + existingProductIngredients);

            Map<String, Set<String>> existingProductHarmfulMap = productHarmfulCombinationCache
                    .computeIfAbsent(existingProductId, id -> computeHarmfulCombinations(existingProductIngredients));


            // Debug: Print harmful combinations map
            System.out.println("Harmful Map for Existing Product: " + existingProductHarmfulMap);


            for (String ingredient : newProductIngredients) {
                System.out.println("Checking component: " + ingredient.trim());

                // Check if any ingredient in the existing product has a harmful combination with the current new product ingredient
                for (Map.Entry<String, Set<String>> entry : existingProductHarmfulMap.entrySet()) {
                    String existingIngredient = entry.getKey();
                    Set<String> harmfulWithSet = entry.getValue();

                    System.out.println("Checking against existing ingredient: " + existingIngredient);

                    if (harmfulWithSet.contains(ingredient.trim())) {
                        System.out.println("Harmful combination detected between " + ingredient.trim() + " in " + newProduct.getName() +
                                " and " + existingIngredient + " in " + existingProduct.getName());
                        return "Harmful combination found: " + ingredient.trim() + " in " + newProduct.getName() +
                                " and " + existingIngredient + " in " + existingProduct.getName() +
                                ". Please choose another product or remove the existing one.";
                        }
                    }
                }
            }

        // Add the product to the routine
        if (!routineProducts.contains(newProductId)) {
            routineProducts.add(newProductId);
            chosenProducts.put(newProductType, routineProducts);
            userRoutine.setChosenProducts(chosenProducts);
            routineRepository.save(userRoutine);
            System.out.println("After saving:");
            printRoutineProductNames(userRoutine.getChosenProducts());
            return newProduct.getName() + " added to routine.";
        } else {
            return "Product is already in the routine.";
        }
    }




    private Map<String, Set<String>> computeHarmfulCombinations(Set<String> productComponents) {
        Map<String, Set<String>> harmfulMap = new HashMap<>();
        for (String component : productComponents) {
            if (harmfulCombinations.containsKey(component)) {
                harmfulMap.put(component, harmfulCombinations.get(component));
            }
        }
        return harmfulMap;
    }

    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    public Routine addRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    private void printRoutineProductNames(Map<String, List<String>> chosenProducts) {
        for (Map.Entry<String, List<String>> entry : chosenProducts.entrySet()) {
            String productType = entry.getKey();
            List<String> productIds = entry.getValue();
            System.out.println("Product Type: " + productType);
            for (String productId : productIds) {
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    System.out.println(" - " + product.getName());
                } else {
                    System.out.println(" - Product ID " + productId + " not found");
                }
            }
        }
            }
        }