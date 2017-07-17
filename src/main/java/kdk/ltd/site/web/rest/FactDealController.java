package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.dto.FactDealDTO;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping("deals")
public class FactDealController {

    @Inject
    private DealService<Deal, FactDealDTO> dealService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public FactDealDTO getOne(@PathVariable Long id) {
        return dealService.findDto(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<FactDealDTO> findPage(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return dealService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Deal deal) {
        this.dealService.save(deal);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void createList(@RequestBody List<Deal> deals) {
        this.dealService.save(deals);
    }

    @RequestMapping(method = RequestMethod.GET, params = "search")
    public Page<FactDealDTO> search(
                                 @PageableDefault(size = 10, page = 0) Pageable pageable,
                                 @RequestBody DealSearchCriteria criteria) {
        return dealService.search(criteria, pageable);
    }
}
