package com.lureb.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Category {

    @Id
    private ObjectId id;
    private String description;
    private Set<ObjectId> recipeIds = new HashSet<>();

    public Category() {
        if (id ==null) {
            id = new ObjectId();
        }
    }

    public void addRecipe(Recipe recipe) {
        recipeIds.add(recipe.getId());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
