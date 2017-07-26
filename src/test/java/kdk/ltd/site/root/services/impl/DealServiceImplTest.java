package kdk.ltd.site.root.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.dto.DealDTO;
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
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = RootContextConfiguration.class )
@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_deals.sql" )
@Transactional
public class DealServiceImplTest {

    @Inject
    DealService<Deal, DealDTO> service;

    @Inject
    ProductRepository productRepository;
    @Inject
    PartnerRepository partnerRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    StorageRepository storageRepository;
    @Inject
    ProductInStockRepository productInStockRepository;

    @Inject
    ObjectMapper objectMapper;

    Pageable pageable = new PageRequest(1, 3);

    Deal deal;

    @Before
    public void setUp() {
        DealDetail detail1 =
                new DealDetail(productRepository.getOne(3L), 50, new BigDecimal(12000));
        DealDetail detail2 =
                new DealDetail(productRepository.getOne(4L), 10, new BigDecimal(1200));
        deal =
                new Deal(
                        GenericDeal.Type.PURCHASE,
                        partnerRepository.getOne(1L),
                        true,
                        LocalDateTime.now(),
                        Arrays.asList(detail1, detail2)
                );
    }

    @Test
    public void findTest() {
        Deal deal = service.find(1L);
        Assert.assertNotNull(deal);
    }

    @Test
    public void findAllTest() {
        List<Deal> deals = service.findAll();
        Assert.assertEquals(5, deals.size());
    }

    @Test
    public void findDtoTest() {
        DealDTO dto = service.findDto(5L);
        Assert.assertNotNull(dto);
    }

    @Test
    public void pageFindAllTest() {
        Page<DealDTO> page = service.findAll(pageable);
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
                new DealDetail(productRepository.getOne(3L), 50, new BigDecimal(12000), storageRepository.getOne(1L));

        Deal deal =
                new Deal(
                        GenericDeal.Type.PURCHASE,
                        partnerRepository.getOne(1L),
                        true,
                        LocalDateTime.now(),
                        Collections.singletonList(detail1)
                );

        service.save(deal);

        ProductInStock inStock = productInStockRepository.findOne(1L);

        Assert.assertEquals(detail1.getStorage().getId(),
                inStock.getStorage().getId());
        Assert.assertEquals(detail1.getProduct().getId(),
                inStock.getProduct().getId());
        Assert.assertEquals(new Integer(50), inStock.getQuantity());
        Assert.assertEquals(new BigDecimal(12000).negate(), inStock.getSum());
    }
}