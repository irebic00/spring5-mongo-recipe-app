package com.lureb.controllers;

import com.lureb.commands.RecipeCommand;
import com.lureb.exception.NotFoundException;
import com.lureb.model.Recipe;
import com.lureb.services.RecipeService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(new ObjectId());

        Mockito.when(recipeService.findById(ArgumentMatchers.anyString())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        Mockito.when(recipeService.findById(Mockito.anyString())).thenThrow(new NotFoundException("Recipe not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/222/show"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("errors/404Error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/1/delete"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(recipeService, Mockito.times(1)).deleteRecipeCommandById(Mockito.anyString());
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("id2");

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                        .param("directions", "some directions")
                        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/id2/show"));
    }

    @Test
    public void testPostNewRecipeFormFailedValidation() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("id2");

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("id2");

        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyString())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }
}