package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.entities.RemainingProducts;
import kdk.ltd.site.root.repositories.RemainingProductsRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.services.RemainingProductsService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("remainings")
public class RemainingProductsController {

    @Inject
    private RemainingProductsService service;
    @Inject
    private RemainingProductsRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<RemainingProducts> showForProductAndStorage(@RequestParam(value = "product", required = false) Optional<Long> productId,
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

        return 999999999;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "createNewPeriod", method = RequestMethod.GET)
    public void createNewPeriod(@RequestParam(value = "date")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        this.service.createNewDateTimePoint(dateTime);
    }
}

