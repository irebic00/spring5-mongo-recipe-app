package com.lureb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@EqualsAndHashCode
@ToString(exclude = {"uom"})
public class Ingredient {

    @Id
    private ObjectId id;

    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

    public Ingredient() {
        if (id == null) {
             id = new ObjectId();
        }
    }
}
