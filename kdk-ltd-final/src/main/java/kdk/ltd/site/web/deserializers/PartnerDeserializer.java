package kdk.ltd.site.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import kdk.ltd.site.root.entities.Partner;
import kdk.ltd.site.root.repositories.PartnerRepository;

import javax.inject.Inject;
import java.io.IOException;



public class PartnerDeserializer extends JsonDeserializer<Partner> {
    @Inject
    private PartnerRepository repository;

    public PartnerDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public Partner deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return repository.getOne(jp.getValueAsLong());
    }
}
