package com.lureb.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import org.bson.types.ObjectId;

import java.io.IOException;

public class StringToObjectIdSerializer extends JsonSerializer<ObjectId> {

    private ObjectMapper objectMapper;

    public StringToObjectIdSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(objectId.toString());
    }

    public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(jsonParser);

        try {
            LegacyFormat legacyFormat = objectMapper.treeToValue(jsonNode, LegacyFormat.class);
            ObjectId id = new ObjectId(legacyFormat.getDate(),
                    legacyFormat.getMachineIdentifier(),
                    legacyFormat.getProcessIdentifier(),
                    legacyFormat.getCounter());
            if (ObjectId.isValid(id.toHexString())) {
                return id;
            }
        } catch (Exception ignore) {}
        return null;
    }

}
