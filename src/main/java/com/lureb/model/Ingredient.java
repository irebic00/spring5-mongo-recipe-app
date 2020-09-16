package com.lureb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document
@EqualsAndHashCode
@ToString(exclude = {"uom"})
public class Ingredient {

    @Id
    private String id = UUID.randomUUID().toString();

    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

}
