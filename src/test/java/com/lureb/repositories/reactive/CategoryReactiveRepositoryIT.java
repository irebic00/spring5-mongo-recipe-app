package com.lureb.repositories.reactive;

import com.lureb.model.Category;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Test
    @DirtiesContext // if we mess up db
    public void provisionAndFindCategory() {
        Category category = new Category();
        ObjectId id = new ObjectId();
        category.setId(id);
        category.setDescription("test");
        categoryReactiveRepository.save(category).block();


        assertEquals("test", categoryReactiveRepository.findById(id).block().getDescription());
    }
}