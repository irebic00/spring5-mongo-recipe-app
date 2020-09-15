package com.lureb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image", "ingredients", "categories", "notes"})
public class Recipe {

    @Id
    private String id;

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

    @DBRef
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
}
