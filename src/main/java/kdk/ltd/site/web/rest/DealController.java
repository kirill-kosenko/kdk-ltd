package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.dto.DealDTO;
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
public class DealController {

    @Inject
    private DealService<Deal, DealDTO> dealService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DealDTO getOne(@PathVariable Long id) {
        return dealService.findDto(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<DealDTO> findPage(
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Deal deal) {
        dealService.update(id, deal );
    }

    @RequestMapping(method = RequestMethod.GET, params = "search")
    public Page<DealDTO> search(
                                 @PageableDefault Pageable pageable,
                                 @RequestBody DealSearchCriteria criteria) {
        return dealService.search(criteria, pageable);
    }
}
