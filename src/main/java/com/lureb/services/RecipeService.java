package com.lureb.services;

import com.lureb.commands.RecipeCommand;
import com.lureb.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findRecipeCommandById(String id);

    void deleteRecipeCommandById(String id);
}
