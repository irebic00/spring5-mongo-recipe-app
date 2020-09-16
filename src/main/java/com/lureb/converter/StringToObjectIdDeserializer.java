package com.lureb.converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.Date;

public class StringToObjectIdDeserializer extends JsonDeserializer<ObjectId> {

    private ObjectMapper objectMapper;

    public StringToObjectIdDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
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
