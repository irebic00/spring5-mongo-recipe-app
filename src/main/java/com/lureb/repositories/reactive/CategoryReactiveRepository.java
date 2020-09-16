package com.lureb.repositories.reactive;

import com.lureb.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, ObjectId> {

    Mono<Category> findByDescription(String description);
}
