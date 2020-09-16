package com.lureb.repositories;

import com.lureb.model.UnitOfMeasure;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, ObjectId> {

    Optional<UnitOfMeasure> findByUom(String uom);
}
