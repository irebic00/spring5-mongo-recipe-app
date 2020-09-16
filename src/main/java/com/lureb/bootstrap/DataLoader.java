package com.lureb.bootstrap;

import com.lureb.model.*;
import com.lureb.repositories.CategoryRepository;
import com.lureb.repositories.RecipeRepository;
import com.lureb.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Profile("default")
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                      UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    private List<Recipe> bootstrappedData() {

        List<Recipe> recipes = new ArrayList<>();

        Category mexican = categoryRepository.findByDescription("Mexican").orElseThrow(() -> new RuntimeException("Category mexican not provisioned"));
        log.info("Saved category: {}", mexican);
        Category fastFood = categoryRepository.findByDescription("Fast Food").orElseThrow(() -> new RuntimeException("Category fastFood not provisioned"));
        log.info("Saved category: {}", fastFood);

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Easy to cook");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(BigDecimal.valueOf(1));
        ingredient1.setUom(unitOfMeasureReactiveRepository.findByUom("Tablespoon").block());
        ingredient1.setDescription("clean guacamole");

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.addCategory(mexican);
        mexican.addRecipe(perfectGuacamole);
        categoryRepository.save(mexican);
        perfectGuacamole.setCookTime(10);
        perfectGuacamole.setPrepTime(5);
        perfectGuacamole.setDescription("Traditional Mexican dish.");
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setDirections("Just cook it");
        perfectGuacamole.setServings(2);
        perfectGuacamole.setSource("Coolinarika");
        perfectGuacamole.setUrl("http://www.coolinarika.hr");
        perfectGuacamole.setNotes(perfectGuacamoleNotes);
        perfectGuacamole.addIngredient(ingredient1);
        ingredient1.setRecipeId(perfectGuacamole.getId());
        log.info("Saved recipe: {}", perfectGuacamole);


        Ingredient ingredient2 = new Ingredient();
        ingredient2.setAmount(BigDecimal.valueOf(1));
        ingredient2.setUom(unitOfMeasureReactiveRepository.findByUom("Tablespoon").block());
        ingredient2.setDescription("clean chicken");

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Kind off Easy to cook");
        Recipe spicyGrilledChickenTacos = new Recipe();
        spicyGrilledChickenTacos.addCategory(fastFood);
        fastFood.addRecipe(spicyGrilledChickenTacos);
        categoryRepository.save(fastFood);
        spicyGrilledChickenTacos.setCookTime(120);
        spicyGrilledChickenTacos.setPrepTime(15);
        spicyGrilledChickenTacos.setDescription("Super hot chick.");
        spicyGrilledChickenTacos.setDifficulty(Difficulty.EASY);
        spicyGrilledChickenTacos.setDirections("Just cook it");
        spicyGrilledChickenTacos.setServings(5);
        spicyGrilledChickenTacos.setSource("Coolinarika");
        spicyGrilledChickenTacos.setUrl("http://www.coolinarika.hr");
        spicyGrilledChickenTacos.setNotes(spicyGrilledChickenTacosNotes);
        spicyGrilledChickenTacos.addIngredient(ingredient2);
        ingredient2.setRecipeId(spicyGrilledChickenTacos.getId());
        log.info("Saved recipe: {}", spicyGrilledChickenTacos);

        recipes.add(perfectGuacamole);
        recipes.add(spicyGrilledChickenTacos);
        return recipes;
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        unitOfMeasureReactiveRepository.save(uom1).block();

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        unitOfMeasureReactiveRepository.save(uom2).block();

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        unitOfMeasureReactiveRepository.save(uom3).block();

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        unitOfMeasureReactiveRepository.save(uom4).block();

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        unitOfMeasureReactiveRepository.save(uom5).block();

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        unitOfMeasureReactiveRepository.save(uom6).block();

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Pint");
        unitOfMeasureReactiveRepository.save(uom7).block();

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Dash");
        unitOfMeasureReactiveRepository.save(uom8).block();
    }

    @Override
    // @Transactional only on SQL dbs, no need for mongo (javax.transaction.Transactional)
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadCategories();
        loadUom();
        recipeRepository.saveAll(bootstrappedData());
    }
}
