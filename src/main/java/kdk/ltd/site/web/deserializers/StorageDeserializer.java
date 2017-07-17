package kdk.ltd.site.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import kdk.ltd.site.root.repositories.StorageRepository;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import kdk.ltd.site.root.entities.Storage;

import javax.inject.Inject;
import java.io.IOException;


public class StorageDeserializer extends JsonDeserializer<Storage> {

    @Inject
    private StorageRepository repository;

    public StorageDeserializer() {
       SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public Storage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return repository.getOne(jp.getValueAsLong());
    }
}
