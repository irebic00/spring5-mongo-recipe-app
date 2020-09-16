package com.lureb.services;

import com.lureb.commands.IngredientCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.Ingredient;
import com.lureb.model.Recipe;
import com.lureb.model.UnitOfMeasure;
import com.lureb.repositories.reactive.RecipeReactiveRepository;
import com.lureb.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private final ModelConverter modelConverter;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, ModelConverter modelConverter) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeReactiveRepository
                .findById(new ObjectId(recipeId))
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().toString().equals(ingredientId))
                .single()
                .map(ingredient -> modelConverter.convertValue(ingredient, IngredientCommand.class));
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        return recipeReactiveRepository
                .findById(new ObjectId(ingredientCommand.getRecipeId()))
                .switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(recipe -> {
                    UnitOfMeasure uom = unitOfMeasureReactiveRepository.findById(new ObjectId(ingredientCommand.getUom().getId())).block();
                    Ingredient ingredient = modelConverter.convertValue(ingredientCommand, Ingredient.class);
                    ingredient.getUom().setUom(uom.getUom());
                    recipe.addOrReplaceIngredient(ingredient);
                    return recipeReactiveRepository.save(recipe).then(Mono.just(ingredientCommand));
                });
    }

    @Override
    public Mono<IngredientCommand> deleteIngredientById(String recipeId, String ingredientId) {
        return recipeReactiveRepository.findById(new ObjectId(recipeId))
                .switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(recipe -> {
                    recipe.removeIngredient(ingredientId);
                    return recipeReactiveRepository.save(recipe).then(Mono.empty());
                });
    }
}
