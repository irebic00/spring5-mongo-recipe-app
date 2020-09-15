package com.lureb.services;

import com.lureb.exception.NotFoundException;
import com.lureb.commands.RecipeCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.Recipe;
import com.lureb.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final ModelConverter modelConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelConverter modelConverter) {
        this.recipeRepository = recipeRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("this is lombok thingies");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return new HashSet<>(recipes);
    }

    @Override
    public Recipe findById(String id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found for id " + id +"!"));
    }

    @Override
    // @Transactional only on SQL dbs, no need for mongo (javax.transaction.Transactional)
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = modelConverter.convertValue(command, Recipe.class);
        return modelConverter.convertValue(recipeRepository.save(detachedRecipe), RecipeCommand.class);
    }

    @Override
    public RecipeCommand findRecipeCommandById(String id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        return modelConverter.convertValue(recipe, RecipeCommand.class);
    }

    @Override
    public void deleteRecipeCommandById(String id) {
        recipeRepository.deleteById(id);
    }
}
