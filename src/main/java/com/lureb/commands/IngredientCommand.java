package com.lureb.commands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class IngredientCommand {
    private String id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    @DBRef
    private UnitOfMeasureCommand uom;

    public IngredientCommand() {
        if (id == null){
            id = UUID.randomUUID().toString();
        }
    }
}
