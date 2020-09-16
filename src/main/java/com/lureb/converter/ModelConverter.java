package com.lureb.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    private final ObjectMapper objectMapper;

    public ModelConverter() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule()
                        .addDeserializer(String.class, new ObjectIdToStringDeserializer()))
                .registerModule(new SimpleModule()
                        .addDeserializer(ObjectId.class, new StringToObjectIdDeserializer()));
    }

    public <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        return objectMapper.convertValue(fromValue, toValueType);
    }
}
