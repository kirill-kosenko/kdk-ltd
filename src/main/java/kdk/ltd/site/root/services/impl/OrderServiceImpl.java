package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.dto.OrderDTO;
import kdk.ltd.site.root.entities.Order;
import kdk.ltd.site.root.repositories.OrderRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements DealService<Order, OrderDTO> {

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private ProductRepository productRepository;


    protected JpaRepository<Order, Long> getRepository() {
        return this.orderRepository;
    }

    @Override
    public void save(Order document) {
        this.orderRepository.save(document);
    }

    @Override
    public Order find(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDTO findDto(Long id) {
        Order order = orderRepository.findOne(id);
        return buildDTO(order);
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return new PageImpl<>(transformDocumentsInDTOs(orders), pageable, orders.getTotalElements());
    }

    protected List<OrderDTO> transformDocumentsInDTOs(Iterable<Order> documents) {
        List<OrderDTO> results = new LinkedList<>();
        documents.forEach(
                d -> results.add( buildDTO(d) ));
        return results;
    }


    protected OrderDTO buildDTO(Order document) {
        return OrderDTO.build(document);
    }

    @Override
    public void save(List<Order> documents) {
        orderRepository.save(documents);
    }

    @Override
    public Page<OrderDTO> search(DealSearchCriteria criteria, Pageable pageable) {
        throw new UnsupportedOperationException("Search operation is unsupported yet");
    }

    @Override
    public void update(Long id, Order deal) {

    }
}
