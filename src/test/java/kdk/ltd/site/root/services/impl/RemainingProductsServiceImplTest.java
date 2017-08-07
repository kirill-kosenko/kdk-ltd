package kdk.ltd.site.root.services.impl;


import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.RemainingProducts;
import kdk.ltd.site.root.repositories.PartnerRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.repositories.StorageRepository;
import kdk.ltd.site.root.repositories.UserRepository;
import kdk.ltd.site.root.services.DealService;
import kdk.ltd.site.root.services.RemainingProductsService;
import kdk.ltd.site.root.exceptions.NegativeBalanceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = RootContextConfiguration.class)
@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:stock.sql" )
@Transactional
public class RemainingProductsServiceImplTest {

    @Inject
    RemainingProductsService remainingProductsService;

    @PersistenceContext
    EntityManager em;

    @Inject
    DealService<Deal> service;

    @Inject
    ProductRepository productRepository;
    @Inject
    PartnerRepository partnerRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    StorageRepository storageRepository;

    @Test                                      //TODO: add product and storage
    public void findOneTest() {
        RemainingProducts inStock = remainingProductsService.findOne(
                1L);
        Assert.assertNotNull(inStock);
    }



    @Test
    public void findByStorageTest() {
        List<RemainingProducts> list = remainingProductsService.findByStorageId(4L);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void findByProductTest() {
        List<RemainingProducts> list = remainingProductsService.findByProductId(3L);
        Assert.assertEquals(1L, list.size());
    }

    @Test
    public void updateProductsInStockTest() {
        RemainingProducts rmp1 =
                new RemainingProducts(productRepository.getOne(3L), storageRepository.getOne(4L), 50, new BigDecimal(-12000));

        RemainingProducts rmp2 =
                new RemainingProducts(productRepository.getOne(3L), storageRepository.getOne(4L), -40, new BigDecimal(12000));
        remainingProductsService.saveOrUpdate(Arrays.asList(rmp1, rmp2));
        em.flush();
        RemainingProducts inStock = remainingProductsService.findOne(
                1L);

        Assert.assertEquals(new Integer(63), inStock.getQuantity());
        Assert.assertEquals(new BigDecimal(19200), inStock.getSum());
    }

    @Test(expected = NegativeBalanceException.class)
    public void updateProductsInStockExceptionTest() {
        DealDetail detail =
                new DealDetail(productRepository.getOne(3L), -60, new BigDecimal(19200), storageRepository.getOne(4L));

        remainingProductsService.saveOrUpdate(Collections.singletonList(
                new RemainingProducts(detail.getProduct(), detail.getStorage(), detail.getQuantity(), detail.getSum())
        ));
        em.flush();
    }

    @Test
    public void testPurchaseUpdate() {

    }

    @Test
    public void testCreatePeriod() {

    }

    @Test
    public void testDeleteAll() {

    }


}