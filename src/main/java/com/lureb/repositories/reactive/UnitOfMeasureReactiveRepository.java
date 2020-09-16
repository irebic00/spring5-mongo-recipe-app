package com.lureb.repositories.reactive;

import com.lureb.model.UnitOfMeasure;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, ObjectId> {

    Mono<UnitOfMeasure> findByUom(String uom);
}
