package com.lureb.services;

import com.lureb.commands.IngredientCommand;
import com.lureb.commands.RecipeCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.exception.NotFoundException;
import com.lureb.model.Ingredient;
import com.lureb.model.Recipe;
import com.lureb.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final ModelConverter modelConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, ModelConverter modelConverter) {
        this.recipeRepository = recipeRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        RecipeCommand recipeCommand = modelConverter.convertValue(recipe.orElse(null), RecipeCommand.class);
        if (recipeCommand == null) {
            throw new NotFoundException("Recipe not found!");
        }

        IngredientCommand ingredientCommand = recipeCommand
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .collect(Collectors.toList())
                .get(0);
        try {
            ingredientCommand.setRecipeId(recipeId);
            return ingredientCommand;
        } catch (NullPointerException exception){
            throw new NotFoundException("Ingredient not found!");
        }
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe not found");
        }
        Recipe recipe = recipeOptional.get();

        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients.stream().noneMatch(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))) {
            Ingredient newIngredient = modelConverter.convertValue(ingredientCommand, Ingredient.class);
            recipe.addIngredient(newIngredient);
            recipeRepository.save(recipe);
            ingredientCommand.setId(newIngredient.getId());
            return ingredientCommand;
        }

        List<Ingredient> selectedIngredient = ingredients
                .stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId()))
                .collect(Collectors.toList());
        if (selectedIngredient.isEmpty()) {
            throw new NotFoundException("Selected ingredient is not found for given ricipe");
        }

        recipe.getIngredients().remove(selectedIngredient.get(0));

        Ingredient newIngredient = modelConverter.convertValue(ingredientCommand, Ingredient.class);
        recipe.getIngredients().add(newIngredient);
        recipeRepository.save(recipe);

        return ingredientCommand;
    }

    @Override
    public void deleteIngredientById(String recipeId, String ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundException("Recipe not found"));
        Ingredient ingredient = recipe
                .getIngredients()
                .stream()
                .filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ingredient not found"));

        recipe.getIngredients().remove(ingredient);
        recipeRepository.save(recipe);
    }
}
