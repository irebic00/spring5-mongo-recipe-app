package com.lureb.model;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document
public class UnitOfMeasure {

    @Id
    private ObjectId id;
    private String uom;

    public UnitOfMeasure() {
        if (id == null) {
            id  = new ObjectId();
        }
    }
}
