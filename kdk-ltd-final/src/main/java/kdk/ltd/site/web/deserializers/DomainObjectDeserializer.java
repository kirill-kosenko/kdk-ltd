package kdk.ltd.site.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kdk.ltd.site.root.entities.PersistableObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import kdk.ltd.site.root.repositories.DomainObjectRepository;

import javax.inject.Inject;
import java.io.IOException;


@Component
public class DomainObjectDeserializer extends JsonDeserializer<PersistableObject> {

    @Inject
    private DomainObjectRepository rep;

    public DomainObjectDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PersistableObject deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);

        String currentName = jp.getCurrentName();

        String className = currentName.substring(0, 1).toUpperCase()
                + currentName.substring(1, currentName.length() - "Id".length());

        Integer id = node.asInt();

        return rep.find(id, className);
    }

}
