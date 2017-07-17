package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.entities.ProductInStock;
import kdk.ltd.site.root.repositories.ProductInStockRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.services.ProductInStockService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("remainings")
public class ProductInStockController {

    @Inject
    private ProductInStockService service;
    @Inject
    private ProductInStockRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProductInStock> showForProductAndStorage(@RequestParam(value = "product", required = false) Optional<Long> productId,
                                                    @RequestParam(value = "storage", required = false) Optional<Long> storageId) {

        if (productId.isPresent() && storageId.isPresent() )
            return Collections.singletonList(repository.findByProductIdAndStorageId(productId.get(), storageId.get()).get());
        if (productId.isPresent())
            return repository.findByProductId(productId.get());
        if (storageId.isPresent())
            return repository.findByStorageId(storageId.get());
        return repository.findAll();
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.GET)
    public @ResponseBody Integer productQuantity(@PathVariable Long productId) {
        Integer i = repository.findReamainingQuantityForProduct(productId);
        return i;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "createNewPeriod", method = RequestMethod.GET)
    public void createNewPeriod(@RequestParam(value = "date", required = true)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        this.service.createPeriod(date);
    }
}

