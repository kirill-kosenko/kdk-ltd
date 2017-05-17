package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.repositories.StorageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.entities.Storage;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping("storages")
public class StorageController {

    @Inject
    private StorageRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Storage> showAllStorages() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Storage storage) {
        this.repository.save(storage);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        this.repository.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, Storage storage) {
        this.repository.update(storage.getName(), id);

    }
}
