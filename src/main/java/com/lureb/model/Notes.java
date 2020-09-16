package com.lureb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
public class Notes {

    @Id
    private ObjectId id;

    private Recipe recipe;

    private String recipeNotes;

    public Notes() {
        if (id == null) {
            id  = new ObjectId();
        }
    }
}
