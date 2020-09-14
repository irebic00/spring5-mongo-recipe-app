package com.lureb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"uom", "recipe"})
@Document
public class Ingredient {
    @Id
    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

    @JsonBackReference
    @DBRef
    private Recipe recipe;

}
