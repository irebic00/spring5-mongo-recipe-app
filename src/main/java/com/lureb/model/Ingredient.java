package com.lureb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"uom", "recipe"})
public class Ingredient {
    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

    @JsonBackReference
    private Recipe recipe;

}
