package kdk.ltd.site.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.repositories.ProductRepository;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.inject.Inject;
import java.io.IOException;




public class ProductDeserializer extends JsonDeserializer<Product> {

    @Inject
    private ProductRepository repository;

    public ProductDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return repository.getOne(jp.getValueAsLong());
    }
}
