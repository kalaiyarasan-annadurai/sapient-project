package com.pulicies.sapient.dto;

import java.util.List;
import com.pulicies.sapient.model.Recipe;

public class ReceipeResponse {
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
