package kdk.ltd.site.root.repositories;

import kdk.ltd.config.RootContextConfiguration;
import kdk.ltd.site.root.entities.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootContextConfiguration.class)
@Transactional
public class StorageRepositoryTest {

    @Inject
    private StorageRepository repository;

    private static List<Storage> list;

    @Before
    public void setUp() {
        list = Arrays.asList(
                new Storage("Storage 1"),
                new Storage("Storage 2"),
                new Storage("Storage 3"),
                new Storage("Storage 4"),
                new Storage("Storage 5")
        );


    }

    @Test
    public void findOneTest() {
        Storage storage = repository.findOne(1L);
        Assert.assertEquals(list.get(0).getName(), storage.getName());
    }

    @Test
    public void findAllTest() {
        List<Storage> storages = repository.findAll();
        Assert.assertEquals(list.size(), storages.size());
    }

    @Test
    public void findAllNamesTest() {
        List<String> names = repository.findAllNames();

        Assert.assertEquals(5, names.size());
        for (int i = 0; i < names.size(); i++)
            Assert.assertEquals(list.get(i).getName(), names.get(i));
    }

    @Test
    public void findByNameTest() {
        Storage actual = repository.findByName("Storage 2");
        Assert.assertEquals(list.get(1).getName(), actual.getName());
    }
}