package com.lureb.commands;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;

@Getter
@Setter
public class IngredientCommand {
    private ObjectId id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    @DBRef
    private UnitOfMeasureCommand uom;

    public IngredientCommand() {
        if (id == null){
            id = new ObjectId();
        }
    }
}
