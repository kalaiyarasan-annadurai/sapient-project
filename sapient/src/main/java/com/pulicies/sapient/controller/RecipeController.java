package com.pulicies.sapient.controller;

import com.pulicies.sapient.model.Recipe;
import com.pulicies.sapient.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Loads recipes from an external source into the system
     */
    @PostMapping("/load")
    public ResponseEntity<String> loadRecipes() {
        recipeService.loadRecipesFromExternalTemplate();
        return ResponseEntity.ok("Recipes loaded successfully");
    }

    /**
     * Returns all available recipes
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        if (recipes == null || recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    /**
     * Searches recipes by query string (e.g. name or cuisine)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String query) {
        if (query == null || query.trim().length() < 3) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Recipe> results = recipeService.searchRecipes(query.trim());

        if (results == null || results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    /**
     * Fetch recipe by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Recipe recipe = recipeService.getById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recipe);
    }
}
