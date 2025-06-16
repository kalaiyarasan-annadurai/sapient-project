package com.pulicies.sapient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pulicies.sapient.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe,Long>{

    List<Recipe> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(String name, String cuisine);

} 
