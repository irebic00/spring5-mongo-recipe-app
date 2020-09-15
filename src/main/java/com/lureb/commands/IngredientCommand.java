package com.lureb.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class IngredientCommand {
    private String id;
    @JsonIgnore
    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

    public IngredientCommand() {
        if (id == null){
            id = UUID.randomUUID().toString();
        }
    }
}
