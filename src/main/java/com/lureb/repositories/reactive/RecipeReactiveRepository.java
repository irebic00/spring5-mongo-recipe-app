package com.lureb.repositories.reactive;

import com.lureb.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
