package com.lureb.services;

import com.lureb.commands.IngredientCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.Ingredient;
import com.lureb.model.Recipe;
import com.lureb.repositories.IngredientRepository;
import com.lureb.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;

    ModelConverter modelConverter;

    IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelConverter = new ModelConverter();
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientRepository, modelConverter);
    }

    @Test
    public void findByRecipeIdAndId() {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("id1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("id1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("id1");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("id3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("id1", "id3");

        //when
        assertEquals("id3", ingredientCommand.getId());
        assertEquals("id1", ingredientCommand.getRecipeId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("id3");
        command.setRecipeId("id2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("id3");

        Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(recipeOptional);
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals("id3", savedCommand.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(Mockito.any(Recipe.class));

    }

    @Test
    public void testDeleteIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("id3");
        command.setRecipeId("id2");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("id3");

        Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(savedRecipe));

        //when
        ingredientService.deleteIngredientById("id2", "id3");

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(ingredientRepository, Mockito.times(1)).delete(Mockito.any());
    }
}