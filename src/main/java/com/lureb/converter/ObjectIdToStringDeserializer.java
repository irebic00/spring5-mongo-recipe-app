package com.lureb.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdToStringDeserializer extends JsonDeserializer<String> {

    private ObjectMapper objectMapper;

    public ObjectIdToStringDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(jsonParser);

        try {
            LegacyFormat legacyFormat = objectMapper.treeToValue(jsonNode, LegacyFormat.class);
            ObjectId id = new ObjectId(legacyFormat.getDate(),
                    legacyFormat.getMachineIdentifier(),
                    legacyFormat.getProcessIdentifier(),
                    legacyFormat.getCounter());
            if (ObjectId.isValid(id.toHexString())) {
                return id.toString();
            }
        } catch (Exception ignore) {}
        return jsonNode.asText();
    }
}
