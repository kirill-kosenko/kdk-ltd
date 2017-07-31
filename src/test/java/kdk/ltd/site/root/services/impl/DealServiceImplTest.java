package kdk.ltd.site.root.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.dto.DealDto;
import kdk.ltd.site.root.entities.*;
import kdk.ltd.site.root.repositories.*;
import kdk.ltd.site.root.services.DealService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = RootContextConfiguration.class )
@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "classpath:insert_deals.sql", "classpath:stock.sql" } )
@Transactional
public class DealServiceImplTest {

    @Inject
    DealService<Deal> service;
    @Inject
    DealDetailRepository detailRepository;
    @Inject
    DealRepository dealRepository;

    @Inject
    ProductRepository productRepository;
    @Inject
    PartnerRepository partnerRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    StorageRepository storageRepository;
    @Inject
    RemainingProductsRepository remainingProductsRepository;

    @PersistenceContext
    EntityManager em;

    @Inject
    ObjectMapper objectMapper;

    Pageable pageable = new PageRequest(1, 3);

    Deal deal;

    @Before
    public void setUp() {
        DealDetail detail1 =
                new DealDetail(productRepository.getOne(3L), 50, new BigDecimal(-12000));
        DealDetail detail2 =
                new DealDetail(productRepository.getOne(4L), 10, new BigDecimal(-1200));
        deal =
                new Deal(
                        GenericDeal.Type.PURCHASE,
                        partnerRepository.getOne(1L),
                        true,
                        LocalDateTime.now()

                );
        deal.getDetails().addAll(Arrays.asList(detail1, detail2));
        detail1.setDeal(deal);
        detail2.setDeal(deal);
    }

    @Test
    public void findTest() {
        Deal deal = service.find(1L);
        System.out.println(deal.getDetails().size());
        Assert.assertNotNull(deal);
    }

    @Test
    public void findAllTest() {
        List<Deal> deals = service.findAll();
        Assert.assertEquals(5, deals.size());
    }

    @Test
    public void pageFindAllTest() {
        Page<Deal> page = service.findAll(pageable);
        Assert.assertEquals(3, page.getSize());
        Assert.assertEquals(5, page.getTotalElements());
        Assert.assertEquals(2, page.getTotalPages());
    }

    @Test
    public void saveTest() {
        service.save(deal);
        Deal actual = service.find(6L);

        Assert.assertEquals(deal, actual);
    }

    @Test
    public void saveAllTest() {

    }

    @Test
    public void searchTest() {

    }

    @Test
    public void whenSavesDeal_updatesProductInStock_thenCorrect() {
        DealDetail detail1 =
                new DealDetail(productRepository.getOne(3L), 50, new BigDecimal(-12000), storageRepository.getOne(4L));

        Deal deal =
                new Deal(
                        GenericDeal.Type.PURCHASE,
                        partnerRepository.getOne(1L),
                        true,
                        LocalDateTime.now(),
                        Collections.singletonList(detail1)
                );
        detail1.setDeal(deal);

        service.save(deal);

        RemainingProducts inStock = remainingProductsRepository.findOne(1L);

        Assert.assertEquals(detail1.getStorage().getId(),
                inStock.getStorage().getId());
        Assert.assertEquals(detail1.getProduct().getId(),
                inStock.getProduct().getId());
        Assert.assertEquals(new Integer(103), inStock.getQuantity());
        Assert.assertEquals(new BigDecimal(7200), inStock.getSum());
    }

    @Test
    public void updateTest() {
        Deal deal = new Deal(
                GenericDeal.Type.PURCHASE,
                partnerRepository.getOne(1L),
                false,
                LocalDateTime.of(2016, 9, 1, 0, 0));
        deal.setId(1L);
        deal.setVersion(0);

        DealDetail detail = new DealDetail(
                productRepository.getOne(3L),
                50,
                new BigDecimal(-10750),
                storageRepository.getOne(4L));
        detail.setId(2147L);
        detail.setVersion(0);
        DealDetail detail1 = new DealDetail(
                productRepository.getOne(4L),
                10,
                new BigDecimal(-1150),
                storageRepository.getOne(4L)
        );
        detail1.setId(2148L);
        detail1.setVersion(0);

        deal.addAll(Arrays.asList(detail, detail1));
        deal.setPartner( partnerRepository.getOne(2L) );

        long dealSize = 5;
        long detailSize = 16;

        service.update(deal);
        em.flush();

        Assert.assertEquals(dealSize, dealRepository.count());
        Assert.assertEquals(detailSize, detailRepository.count());
    }
}