package com.pulicies.sapient.service;

import com.pulicies.sapient.dto.ReceipeResponse;
import com.pulicies.sapient.exception.RecipeNotFoundException;
import com.pulicies.sapient.model.Recipe;
import com.pulicies.sapient.repository.RecipeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private static final String EXTERNAL_RECIPE_API = "https://dummyjson.com/recipes";

    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        this.restTemplate = new RestTemplate(); 
    }

    /**
     * Loads recipes from an external dummy API and persists them in the database.
     */
    public void loadRecipesFromExternalTemplate() {
        logger.info("Fetching recipes from external API...");

        ReceipeResponse response = restTemplate.getForObject(EXTERNAL_RECIPE_API, ReceipeResponse.class);

        if (response == null || response.getRecipes() == null || response.getRecipes().isEmpty()) {
            logger.warn("No recipes found from external source.");
            return;
        }

        logger.info("Saving {} recipes to the database.", response.getRecipes().size());
        recipeRepository.saveAll(response.getRecipes());
    }

    /**
     * Returns all recipes in the system.
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        logger.debug("Fetched {} recipes from the database.", recipes.size());
        return recipes;
    }

    /**
     * Searches recipes by name or cuisine using a case-insensitive partial match.
     */
    public List<Recipe> searchRecipes(String query) {
        if (query == null || query.trim().length() < 3) {
            logger.warn("Search query too short or null: '{}'", query);
            return Collections.emptyList();
        }

        List<Recipe> results = recipeRepository
                .findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(query.trim(), query.trim());

        logger.info("Found {} recipes matching query '{}'", results.size(), query);
        return results;
    }

    /**
     * Fetches a recipe by ID. Throws RecipeNotFoundException if not found.
     */
    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with ID " + id + " not found"));
    }
}
