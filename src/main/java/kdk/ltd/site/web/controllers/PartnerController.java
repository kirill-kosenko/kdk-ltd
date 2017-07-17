package kdk.ltd.site.web.controllers;

import kdk.ltd.site.root.repositories.PartnerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.Map;


@Controller
@RequestMapping(value = "partners")
public class PartnerController {

    @Inject
    private PartnerRepository repository;

    @RequestMapping
    public String showAll(
            @RequestParam(value = "p", required = false) String p,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            Map<String, Object> model) {
        model.put("partners", repository.findAll(pageable));
        return "partn/list";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET, produces = "text/html")
    public String showNewPartnerPage(@RequestParam(value = "p", required = false) String p) {
        if (p == null)
            return "partn/form";
        return "partn/form_html";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String showPartnerPage(@PathVariable Long id, Map<String, Object> model) {
        model.put("partner", repository.findOne(id));
        return "partn/edit";
    }
}
