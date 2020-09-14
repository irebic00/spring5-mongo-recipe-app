package com.lureb.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document
public class UnitOfMeasure {

    @Id
    private String id;
    private String uom;

}
