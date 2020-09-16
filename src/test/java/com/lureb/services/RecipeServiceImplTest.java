package com.lureb.services;

import com.lureb.commands.RecipeCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.exception.NotFoundException;
import com.lureb.model.Recipe;
import com.lureb.repositories.RecipeRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    ModelConverter modelConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, modelConverter);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        ObjectId id1 = new ObjectId();
        recipe.setId(id1);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(id1.toString());

        assertNotNull("Null recipe returned", recipeReturned);
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(recipeRepository, Mockito.never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        ObjectId id1 = new ObjectId();
        recipe.setId(id1);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id1.toString());

        Mockito.when(modelConverter.convertValue(Mockito.any(), Mockito.any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findRecipeCommandById(id1.toString());

        assertNotNull("Null recipe returned", commandById);
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(recipeRepository, Mockito.never()).findAll();
    }

    @Test
    public void getRecipesTest() {

        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);


        Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);
        assertEquals(1, recipeService.getRecipes().size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void testRecipeNotFoundException() {
        Optional<Recipe> recipe = Optional.empty();

        Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(recipe);

        Recipe recipeReturned = recipeService.findById("id1");
    }

    @Test
    public void testDeleteById() {

        //given
        String idToDelete = "id2";

        //when
        recipeService.deleteRecipeCommandById(idToDelete);

        //no 'when', since method has void return type

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(Mockito.anyString());
    }
}