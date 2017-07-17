package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping(value = "products")
public class ProductController {

    @Inject
    private GenericService<Product, Long> productService;
    @Inject
    private ProductRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> showAllProducts() {
        return repository.findAllByParentIsNotNull();
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public List<Product> showAllCategories() {
        return repository.findAllByParentIsNull();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Product showOne(@PathVariable Long id) {
        return this.repository.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Product product) {
        productService.save(product);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Product product) {
        productService.update(id, product);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
