package com.lureb.controllers;

import com.lureb.commands.IngredientCommand;
import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.exception.NotFoundException;
import com.lureb.services.IngredientService;
import com.lureb.services.RecipeService;
import com.lureb.services.UnitOfMeasureService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findRecipeCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms().collectList().block());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        ingredientService.saveIngredientCommand(ingredientCommand).block();

        return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredients";
    }

    @GetMapping("recipe/{recipeId}/ingredients/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        if (recipeService.findRecipeCommandById(recipeId) == null) {
            throw new NotFoundException("Recipe not found");
        }


        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms().collectList().block());

        return "recipe/ingredient/ingredientForm";
    }

    @DeleteMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
    public ResponseEntity<Void> deleteIngredient (@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteIngredientById(recipeId, ingredientId).block();
        return ResponseEntity.noContent().build();
    }
}
