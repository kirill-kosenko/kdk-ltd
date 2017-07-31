package kdk.ltd.site.root.services.impl;

import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.dto.OrderDto;
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
public class OrderServiceImpl implements DealService<Order> {

    private OrderRepository orderRepository;

    @Inject
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public void save(List<Order> documents) {
        orderRepository.save(documents);
    }

    @Override
    public Page<Order> search(DealSearchCriteria criteria, Pageable pageable) {
        throw new UnsupportedOperationException("Search operation is unsupported yet");
    }

    @Override
    public void update(Order deal) {

    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(id);
    }
}
