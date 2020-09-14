package com.lureb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image", "ingredients", "categories", "notes"})
public class Recipe {
    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;

    @JsonManagedReference
    private Set<Ingredient> ingredients = new HashSet<>();

    private Byte[] image;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    private Set<Category> categories = new HashSet<>();

    @JsonManagedReference
    private Notes notes;

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.getIngredients().add(ingredient);
        return this;
    }

    public Recipe addCategory(Category category) {
        if (category == null) {
            return null;
        }
        category.getRecipes().add(this);
        this.getCategories().add(category);
        return this;
    }
}
