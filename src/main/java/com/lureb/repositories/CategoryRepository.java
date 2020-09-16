package com.lureb.repositories;

import com.lureb.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, ObjectId> {

    Optional<Category> findByDescription(String description);
}
