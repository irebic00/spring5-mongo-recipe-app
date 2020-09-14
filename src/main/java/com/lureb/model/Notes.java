package com.lureb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
public class Notes {
    private String id;

    @JsonBackReference
    private Recipe recipe;

    private String recipeNotes;

}
