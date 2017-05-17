package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.FactDealDTO;
import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kdk.ltd.site.root.entities.FactDeal;


import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping("deals")
public class FactDealController extends KDKInterceptor {

    @Inject
    private DealService<FactDeal, FactDealDTO> dealService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public FactDealDTO getOne(@PathVariable Long id) {
        return dealService.find(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<FactDealDTO> findPage(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return dealService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody FactDeal deal) {
        this.dealService.save(deal);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void createList(@RequestBody List<FactDeal> deals) {
        this.dealService.save(deals);
    }

    @RequestMapping(method = RequestMethod.GET, params = "search")
    public Page<FactDealDTO> search(
                                 @PageableDefault(size = 10, page = 0) Pageable pageable,
                                 @RequestBody DealSearchCriteria criteria) {
        return dealService.search(criteria, pageable);
    }
}
