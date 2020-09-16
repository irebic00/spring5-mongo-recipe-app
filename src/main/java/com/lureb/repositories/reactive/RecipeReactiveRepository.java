package com.lureb.repositories.reactive;

import com.lureb.model.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, ObjectId> {
}
