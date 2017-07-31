package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.OrderDto;
import kdk.ltd.site.root.entities.Order;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Inject
    private DealService<Order> orderService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<OrderDto> showAll(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<Order> orders = orderService.findAll(pageable);
        return new PageImpl<>(transformDealsInDTOs(orders), pageable, orders.getTotalElements());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public OrderDto getOne(@PathVariable Long id) {
        Order order = orderService.find(id);
        return OrderDto.build(order);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Order order) {
        orderService.save(order);
    }

    protected List<OrderDto> transformDealsInDTOs(Iterable<Order> orders) {
        return StreamSupport.stream(orders.spliterator(), false)
                .map(OrderDto::build)
                .collect(Collectors.toList());
    }
}
