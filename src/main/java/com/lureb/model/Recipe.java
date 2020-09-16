package com.lureb.model;

import com.lureb.exception.NotFoundException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image"})
@Slf4j
public class Recipe {

    @Id
    private String id = UUID.randomUUID().toString();

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Byte[] image;
    private List<Category> categories = new ArrayList<>();
    private Notes notes;

    public Recipe addIngredient(Ingredient ingredient) {
        this.getIngredients().add(ingredient);
        return this;
    }

    public Recipe addCategory(Category category) {
        if (category == null) {
            return null;
        }
        this.getCategories().add(category);
        return this;
    }

    public boolean isIngredientPresent(Ingredient ingredient) {
        return ingredients
                .stream()
                .anyMatch(ingredientSaved -> ingredientSaved.getId().equals(ingredient.getId()));
    }

    public void removeIngredient(String ingredientId) {
        ingredients.remove(ingredients
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ingredient not found with id=" + ingredientId)));
    }

    public void addOrReplaceIngredient(Ingredient ingredient) {
        try {
            removeIngredient(ingredient.getId());
        } catch (NotFoundException ex) {
            log.info("Ingredient not found, will add it!");
        }
        ingredients.add(ingredient);
    }
}
