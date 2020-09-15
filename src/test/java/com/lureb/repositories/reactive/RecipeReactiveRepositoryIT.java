package com.lureb.repositories.reactive;

import com.lureb.model.Category;
import com.lureb.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Test
    @DirtiesContext // if we mess up db
    public void provisionAndFindCategory() {
        Recipe recipe = new Recipe();
        recipe.setId("id1");
        recipe.setDescription("test");
        recipeReactiveRepository.save(recipe).block();


        assertEquals("test", recipeReactiveRepository.findById("id1").block().getDescription());
    }
}