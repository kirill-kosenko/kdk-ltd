package kdk.ltd.site.root.services.impl;

import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.dto.OrderDTO;
import kdk.ltd.site.root.entities.GenericDeal;
import kdk.ltd.site.root.entities.Order;
import kdk.ltd.site.root.entities.OrderDetail;
import kdk.ltd.site.root.repositories.PartnerRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.repositories.UserRepository;
import kdk.ltd.site.root.services.DealService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = RootContextConfiguration.class )
@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_orders.sql" )
@Transactional
public class OrderServiceImplTest {

    @Inject
    DealService<Order, OrderDTO> service;
    @Inject
    PartnerRepository partnerRepository;
    @Inject
    ProductRepository productRepository;
    @Inject
    UserRepository userRepository;

    Pageable pageable = new PageRequest(1, 10);

    @Test
    public void saveOrderTest() {
        Order order = new Order(GenericDeal.Type.PURCHASE, partnerRepository.getOne(1L), false, LocalDateTime.now(), false);
        OrderDetail detail = new OrderDetail(productRepository.getOne(1L), 50, new BigDecimal(12000));
        order.add(detail);
        order.setUser(userRepository.getOne(1L));
        service.save(order);
        Order from = service.find(4L);

        Assert.assertNotNull( from );
    }

    @Test
    public void findDtoTest() {
        OrderDTO dto = service.findDto(1L);
        Assert.assertEquals(GenericDeal.Type.PURCHASE, dto.getType());
        Assert.assertEquals(1L, dto.getId());
        Assert.assertEquals(2, dto.getDetails().size());
    }

    @Test
    public void findDtoPageTest() {
        Page page = service.findAll(pageable);
        Assert.assertEquals(3, page.getTotalElements());
    }

    @Test
    public void findAllTest() {
        List<Order> orders = service.findAll();
        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void saveOrdersTest() {
        OrderDetail detail1 = new OrderDetail(productRepository.getOne(1L), 50, new BigDecimal(12000));
        OrderDetail detail2 = new OrderDetail(productRepository.getOne(2L), 10, new BigDecimal(1200));
        OrderDetail detail3 = new OrderDetail(productRepository.getOne(1L), 10, new BigDecimal(3000));
        OrderDetail detail4 = new OrderDetail(productRepository.getOne(2L), 2, new BigDecimal(400));
        OrderDetail detail5 = new OrderDetail(productRepository.getOne(1L), 20, new BigDecimal(6400));
        Order order1 =
                new Order(
                        GenericDeal.Type.PURCHASE,
                        partnerRepository.getOne(1L),
                        false,
                        LocalDateTime.now(),
                        true
                        );
        order1.getDetails().addAll(Arrays.asList(detail1, detail2));
        detail1.setOrder(order1);
        detail2.setOrder(order1);

        Order order2 =
                new Order(
                        GenericDeal.Type.SELL,
                        partnerRepository.getOne(2L),
                        true,
                        LocalDateTime.now(),
                        true
                );
        order2.getDetails().addAll(Arrays.asList(detail3, detail4));
        detail3.setOrder(order2);
        detail4.setOrder(order2);

        Order order3 =
                new Order(
                        GenericDeal.Type.SELL,
                        partnerRepository.getOne(5L),
                        true,
                        LocalDateTime.now(),
                        true
                );
        order3.getDetails().add(detail5);
        detail5.setOrder(order3);

        service.save(
                Arrays.asList(
                        order1, order2, order3
                ));

        Assert.assertEquals(6, service.findAll().size());
    }

    @Test
    public void searchTest() {

    }
}