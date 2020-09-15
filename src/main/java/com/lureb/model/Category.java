package com.lureb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Document
public class Category {

    @Id
    private String id;

    private String description;

    @DBRef
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        if (recipes.stream().anyMatch(recipeSaved -> recipeSaved.equals(recipe))) {
            recipes.add(recipe);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
