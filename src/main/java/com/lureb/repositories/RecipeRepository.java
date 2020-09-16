package com.lureb.repositories;

import com.lureb.model.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
