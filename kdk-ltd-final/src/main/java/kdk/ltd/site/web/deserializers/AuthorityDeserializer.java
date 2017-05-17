package kdk.ltd.site.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kdk.ltd.site.root.entities.Authority;

import java.io.IOException;


public class AuthorityDeserializer extends JsonDeserializer<Authority> {

    @Override
    public Authority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return new Authority(node.get("authority").asText());
    }
}
