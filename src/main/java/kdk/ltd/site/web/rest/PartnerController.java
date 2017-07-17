package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.PartnerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.entities.Partner;
import kdk.ltd.site.root.repositories.PartnerRepository;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping(value = "partners", produces = "application/json")
public class PartnerController {

    @Inject
    private PartnerRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Partner> showAllPartners()  {
        return repository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Partner showOne(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Partner create(@RequestBody PartnerDTO partner) {
        System.out.println(partner.getLastname() + " " + partner.getFirstname() + " " + partner.getFathername());
        partner.getPhones().forEach(System.out::println);
     //   repository.save(partner);
        return new Partner();
    }

    @RequestMapping(method = RequestMethod.GET, params = {"fullname"})
    public List<Partner> searchBy(@RequestParam(value = "fullname") String fullname) {
        System.err.println(fullname);
        List<Partner> partners = repository.findByNameLikeIgnoreCase("%" + fullname + "%");
        return partners;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateName(@PathVariable Long id, Partner partner) {
        this.repository.update(partner.getName(), id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        this.repository.delete(id);
    }


}
