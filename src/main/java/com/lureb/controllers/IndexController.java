package com.lureb.controllers;

import com.lureb.model.Recipe;
import com.lureb.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index", "/show.html"})
    public String index(Model model) {

        Set<Recipe> recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        log.info("Served {}", recipes);

        return "index";
    }
}
