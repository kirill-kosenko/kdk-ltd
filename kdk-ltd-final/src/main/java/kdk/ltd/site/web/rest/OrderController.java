package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import kdk.ltd.site.root.dto.OrderDTO;
import kdk.ltd.site.root.entities.Order;


import javax.inject.Inject;

@RestController
@RequestMapping("orders")
public class OrderController extends KDKInterceptor {

    @Inject
    private DealService<Order, OrderDTO> orderService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<OrderDTO> showAll(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return orderService.findAll(pageable);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public OrderDTO getOne(@PathVariable Long id) {
        return orderService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Order order) {
        orderService.save(order);
    }

}
