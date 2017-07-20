package kdk.ltd.site.root.services.impl;


import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.dto.DealDTO;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.entities.InStockId;
import kdk.ltd.site.root.entities.ProductInStock;
import kdk.ltd.site.root.repositories.PartnerRepository;
import kdk.ltd.site.root.repositories.ProductRepository;
import kdk.ltd.site.root.repositories.StorageRepository;
import kdk.ltd.site.root.repositories.UserRepository;
import kdk.ltd.site.root.services.DealService;
import kdk.ltd.site.root.services.ProductInStockService;
import kdk.ltd.site.root.services.exceptions.NegativeBalanceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = RootContextConfiguration.class)
@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:stock.sql" )
@Transactional
public class ProductInStockServiceImplTest {

    @Inject
    ProductInStockService productInStockService;

    @PersistenceContext
    EntityManager em;

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

    @Test                                      //TODO: add product and storage
    public void findOneTest() {
        ProductInStock inStock = productInStockService.findOne(new InStockId());
        Assert.assertNotNull(inStock);
    }



    @Test
    public void findByStorageTest() {
        List<ProductInStock> list = productInStockService.findByStorageId(4L);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void findByProductTest() {
        List<ProductInStock> list = productInStockService.findByProductId(3L);
        Assert.assertEquals(1L, list.size());
    }

    @Test
    public void updateProductsInStockTest() {
        DealDetail detail1 =
                new DealDetail(productRepository.getOne(3L), 50, new BigDecimal(-12000), storageRepository.getOne(4L));

        DealDetail detail2 =
                new DealDetail(productRepository.getOne(3L), -40, new BigDecimal(12000), storageRepository.getOne(4L));
        productInStockService.updateProductsInStock(Arrays.asList(detail1, detail2));
        em.flush();
        ProductInStock inStock = productInStockService.findOne(new InStockId());

        Assert.assertEquals(new Integer(13), inStock.getQuantity());
        Assert.assertEquals(new BigDecimal(19200), inStock.getSum());
    }

    @Test(expected = NegativeBalanceException.class)
    public void updateProductsInStockExceptionTest() {
        DealDetail detail =
                new DealDetail(productRepository.getOne(3L), -10, new BigDecimal(3200), storageRepository.getOne(4L));

        productInStockService.updateProductsInStock(Collections.singletonList(detail));
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