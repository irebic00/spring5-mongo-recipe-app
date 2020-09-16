package com.lureb.services;

import com.lureb.exception.NotFoundException;
import com.lureb.model.Recipe;
import com.lureb.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String id, MultipartFile image) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found"));

        try {
            Byte[] imageToSave = ArrayUtils.toObject(image.getBytes());
            recipe.setImage(imageToSave);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Image for recipe with id:{} could not be saved!", id, e);
        }
    }
}
