package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.entities.Product;
import kdk.ltd.site.root.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class ProductServiceImpl extends AbstractGenericService<Product, Long>{

    @Inject
    private ProductRepository repository;

    @Override
    public ProductRepository getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public void update(Long key, Product source) {
        Product target = repository.findOne(key);
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setUnitName(source.getUnitName());
        target.setParent(source.getParent());
    }
}
