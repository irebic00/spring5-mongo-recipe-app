package com.lureb.services;

import com.lureb.commands.IngredientCommand;
import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.Ingredient;
import com.lureb.model.Recipe;
import com.lureb.model.UnitOfMeasure;
import com.lureb.repositories.reactive.RecipeReactiveRepository;
import com.lureb.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

public class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    ModelConverter modelConverter;

    IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelConverter = new ModelConverter();
        ingredientService = new IngredientServiceImpl(recipeReactiveRepository, unitOfMeasureReactiveRepository, modelConverter);
    }

    @Test
    public void findByRecipeIdAndId() {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        ObjectId recipeId1 = new ObjectId();
        recipe.setId(recipeId1);

        Ingredient ingredient1 = new Ingredient();
        ObjectId ingredientId1 = new ObjectId();
        ingredient1.setId(ingredientId1);

        Ingredient ingredient2 = new Ingredient();
        ObjectId ingredientId2 = new ObjectId();
        ingredient2.setId(ingredientId2);

        Ingredient ingredient3 = new Ingredient();
        ObjectId ingredientId3 = new ObjectId();
        ingredient3.setId(ingredientId3);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Mockito.when(recipeReactiveRepository.findById(recipeId1)).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId1.toString(), ingredientId3.toString()).block();

        //when
        assertEquals(ingredient3.getId().toString(), ingredientCommand.getId());
        assertEquals(recipeId1.toString(), ingredientCommand.getRecipeId());
        Mockito.verify(recipeReactiveRepository, Mockito.times(1)).findById(recipeId1);
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        ObjectId id3 = new ObjectId();
        ObjectId id2 = new ObjectId();

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(new ObjectId());

        command.setId(id3.toString());
        command.setRecipeId(id2.toString());
        command.setUom(modelConverter.convertValue(uom, UnitOfMeasureCommand.class));

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(id3);

        Mockito.when(recipeReactiveRepository.findById(Mockito.any(ObjectId.class))).thenReturn(Mono.just(new Recipe()));
        Mockito.when(recipeReactiveRepository.save(Mockito.any())).thenReturn(Mono.just(savedRecipe));
        Mockito.when(unitOfMeasureReactiveRepository.findById(Mockito.any(ObjectId.class))).thenReturn(Mono.just(uom));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals(id3.toString(), savedCommand.getId());
        Mockito.verify(recipeReactiveRepository, Mockito.times(1)).findById(Mockito.any(ObjectId.class));
        Mockito.verify(recipeReactiveRepository, Mockito.times(1)).save(Mockito.any(Recipe.class));

    }

    @Test
    public void testDeleteIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        ObjectId id3 = new ObjectId();
        ObjectId id2 = new ObjectId();
        command.setId(id3.toString());
        command.setRecipeId(id2.toString());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(id3);

        Mockito.when(recipeReactiveRepository.findById(Mockito.any(ObjectId.class))).thenReturn(Mono.just(savedRecipe));

        //when
        ingredientService.deleteIngredientById(id2.toString(), id3.toString());

        //then
        Mockito.verify(recipeReactiveRepository, Mockito.times(1)).findById(Mockito.any(ObjectId.class));
    }
}