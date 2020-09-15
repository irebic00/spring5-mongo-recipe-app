package com.lureb.repositories;

import com.lureb.bootstrap.DataLoader;
import com.lureb.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        DataLoader dataLoader = new DataLoader(categoryRepository, recipeRepository, unitOfMeasureRepository);
        dataLoader.onApplicationEvent(null);
    }

    @Test
    @DirtiesContext // if we mess up db
    public void findByUomTeaspoon() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", Objects.requireNonNull(unitOfMeasure.orElse(null)).getUom());
    }

    @Test
    @DirtiesContext
    public void findByUomCup() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", Objects.requireNonNull(unitOfMeasure.orElse(null)).getUom());
    }
}