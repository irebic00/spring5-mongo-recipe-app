package com.lureb.controllers;

import com.lureb.commands.IngredientCommand;
import com.lureb.commands.RecipeCommand;
import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.services.IngredientService;
import com.lureb.services.RecipeService;
import com.lureb.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyString())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //then
        Mockito.verify(recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyString());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(ingredientCommand));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/2/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(ingredientCommand));
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/2/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("id3");
        command.setRecipeId("id2");

        //when
        Mockito.when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/id2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/id2/ingredients"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("id1");

        //when
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyString())).thenReturn(recipeCommand);
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));

        Mockito.verify(recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyString());

    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/1/ingredients/1/delete"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(ingredientService, Mockito.times(1)).deleteIngredientById(Mockito.anyString(), Mockito.anyString());
    }
}
